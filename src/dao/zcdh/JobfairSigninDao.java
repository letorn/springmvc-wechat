package dao.zcdh;

import model.zcdh.JobfairSignin;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("jobfairSigninDao")
public class JobfairSigninDao extends Store<JobfairSignin> {

	public JobfairSignin get(Long fairId, Long userId) {
		return select("select * from zcdh_jobfair_signin where fair_id=? and user_id=?", new Object[] { fairId, userId });
	}

}
