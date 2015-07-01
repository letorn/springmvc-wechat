package service.zcdh;

import java.util.Date;

import javax.annotation.Resource;

import model.Chatter;
import model.Oauth;
import model.zcdh.Jobfair;
import model.zcdh.JobfairPropaganda;
import model.zcdh.JobfairSignin;
import model.zcdh.JobhunteQuickLoginAccount;
import model.zcdh.JobhunteQuickLoginAccount.JobhunteQuickLoginAccountType;

import org.springframework.stereotype.Service;

import util.WeChatClient;
import dao.ChatterDao;
import dao.zcdh.JobfairDao;
import dao.zcdh.JobfairPropagandaDao;
import dao.zcdh.JobfairSigninDao;
import dao.zcdh.JobhunteQuickLoginAccountDao;

@Service("jobFairService")
public class JobFairService {

	@Resource
	private ChatterDao chatterDao;
	@Resource
	private JobhunteQuickLoginAccountDao jobhunteQuickLoginAccountDao;
	@Resource
	private JobfairSigninDao jobfairSigninDao;
	@Resource
	private JobfairPropagandaDao jobfairPropagandaDao;
	@Resource
	private JobfairDao jobfairDao;

	public JobhunteQuickLoginAccount getJobhunteQuickLoginAccount(String warrantyId) {
		return jobhunteQuickLoginAccountDao.get(JobhunteQuickLoginAccountType.WECHAT, warrantyId);
	}

	public JobfairSignin getJobfairSignin(Long fairId, Long userId) {
		return jobfairSigninDao.get(fairId, userId);
	}

	public JobfairPropaganda getJobfairPropaganda(Long fairId) {
		return jobfairPropagandaDao.getByFairId(fairId);
	}

	public Jobfair getJobfair(Long fairId) {
		return jobfairDao.get(fairId);
	}

	public Boolean deleteJobhunteQuickLoginAccount(String warrantyId) {
		return jobhunteQuickLoginAccountDao.delete(JobhunteQuickLoginAccountType.WECHAT, warrantyId);
	}

	public Chatter access(String code, String state) {
		Chatter chatter = null;
		Oauth oauth = WeChatClient.getOauth(code);
		if (oauth != null) {
			chatter = WeChatClient.getChatter(oauth);
			if (chatter != null) {
				Date now = new Date();
				chatter.setOauthDate(now);
				chatter.setUpdateDate(now);
				Chatter oldChatter = chatterDao.getByOpenId(chatter.getOpenId());
				if (oldChatter == null) {
					chatter.setCreateDate(now);
					chatterDao.add(chatter);
				} else {
					if (chatter.getUpdateDate().getTime() - oldChatter.getUpdateDate().getTime() > 1000 * 60 * 50) {
						chatter.setId(oldChatter.getId());
						chatter.setCreateDate(oldChatter.getCreateDate());
						chatterDao.update(chatter);
					}
				}
			}
		}
		return chatter;
	}

}
