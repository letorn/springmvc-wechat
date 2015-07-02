package controller.zcdh;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Chatter;
import model.zcdh.Jobfair;
import model.zcdh.JobfairPropaganda;
import model.zcdh.JobfairSignin;
import model.zcdh.JobhunteQuickLoginAccount;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.zcdh.JobFairService;

@Controller
@RequestMapping("jobfair/")
public class JobFairController {

	@Resource
	private JobFairService jobFairService;

	@RequestMapping("interviewResults.do")
	public String interviewResults(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null)
			return String.format("redirect:http://www.zcdhjob.com/ent/interview/interviewopt!showInterviewResults.action?fairId=%s&openId=%s", state, chatter.getOpenId());
		return null;
	}

	@RequestMapping("interviewResults2.do")
	public String interviewResults2(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null)
			return String.format("redirect:http://www.zcdhjob.com/entjob/ent/interview/interviewopt!showInterviewResults.action?fairId=%s&openId=%s", state, chatter.getOpenId());
		return null;
	}

	@RequestMapping("jobFair.do")
	public String jobFair(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null)
			return String.format("redirect:http://www.zcdhjob.com/ent/fair/fairOpt!toWeChatMain.action?fairId=%s&openId=%s&sex=%d&country=%s&province=%s&city=%s", state, chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
		return null;
	}

	@RequestMapping("jobFair2.do")
	public String jobFair2(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null)
			return String.format("redirect:http://www.zcdhjob.com/entjob/ent/fair/fairOpt!toWeChatMain.action?fairId=%s&openId=%s&sex=%d&country=%s&province=%s&city=%s", state, chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
		return null;
	}

	@RequestMapping("resumeManage.do")
	public String resumeManage(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null) {
			if (jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId()) == null)
				return String.format("redirect:http://www.zcdhjob.com/jobhunte/account/accountOpt!toBindWeChatAccount.action?openId=%s&sex=%d&country=%s&province=%s&city=%s", chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
			return String.format("redirect:http://www.zcdhjob.com/jobhunte/resume/resumeOpt!select2.action?openId=%s", chatter.getOpenId());
		}
		return null;
	}

	@RequestMapping("resumeManage2.do")
	public String resumeManage2(String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null) {
			if (jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId()) == null)
				return String.format("redirect:http://www.zcdhjob.com/entjob/jobhunte/account/accountOpt!toBindWeChatAccount.action?openId=%s&sex=%d&country=%s&province=%s&city=%s", chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
			return String.format("redirect:http://www.zcdhjob.com/entjob/jobhunte/resume/resumeOpt!select2.action?openId=%s", chatter.getOpenId());
		}
		return null;
	}

	@RequestMapping("bindApp.do")
	public String bindApp(PrintWriter out, HttpServletResponse response, String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null) {
			if (jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId()) != null) {
				if (jobFairService.deleteJobhunteQuickLoginAccount(chatter.getOpenId())) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=utf-8");
					out.write("解绑成功，请返回");
				}
			} else {
				return String.format("redirect:http://www.zcdhjob.com/jobhunte/account/accountOpt!toBindWeChatAccount.action?openId=%s&sex=%d&country=%s&province=%s&city=%s", chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
			}
		}
		return null;
	}

	@RequestMapping("bindApp2.do")
	public String bindApp2(PrintWriter out, HttpServletResponse response, String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null) {
			if (jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId()) != null) {
				if (jobFairService.deleteJobhunteQuickLoginAccount(chatter.getOpenId())) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=utf-8");
					out.write("解绑成功，请返回");
				}
			} else {
				return String.format("redirect:http://www.zcdhjob.com/entjob/jobhunte/account/accountOpt!toBindWeChatAccount.action?openId=%s&sex=%d&country=%s&province=%s&city=%s", chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
			}
		}
		return null;
	}

	@RequestMapping("entPostList.do")
	public String entPostList(PrintWriter out, HttpServletResponse response, String code, String state) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null) {
			if (jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId()) != null) {
				try {
					state = URLDecoder.decode(state, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return String.format("redirect:%s&openId=%s", state, chatter.getOpenId());
			} else {
				return String.format("redirect:http://www.zcdhjob.com/entjob/jobhunte/account/accountOpt!toBindWeChatAccount.action?openId=%s&sex=%d&country=%s&province=%s&city=%s", chatter.getOpenId(), chatter.getSex(), chatter.getCountry(), chatter.getProvince(), chatter.getCity());
			}
		}
		return null;
	}

	@RequestMapping("jobFairBak.do")
	public String jobFairBak(HttpServletRequest request, String code, String state, Long fairId) {
		Chatter chatter = jobFairService.access(code, state);
		if (chatter != null && fairId != null) {
			request.setAttribute("fairId", fairId);
			request.setAttribute("openId", chatter.getOpenId());

			JobhunteQuickLoginAccount jobhunteQuickLoginAccount = jobFairService.getJobhunteQuickLoginAccount(chatter.getOpenId());
			if (jobhunteQuickLoginAccount != null) {
				request.setAttribute("userId", jobhunteQuickLoginAccount.getUserId());

				JobfairSignin jobfairSignin = jobFairService.getJobfairSignin(fairId, jobhunteQuickLoginAccount.getUserId());
				if (jobfairSignin != null)
					request.setAttribute("signIn", "signIn");
			}

			JobfairPropaganda jobfairPropaganda = jobFairService.getJobfairPropaganda(fairId);
			if (jobfairPropaganda != null) {
				if (jobfairPropaganda.getPropagandaContent() != null) {
					JSONObject jobfairContent = JSONObject.fromObject(jobfairPropaganda.getPropagandaContent());
					if (jobfairContent.has("headPic"))
						request.setAttribute("headPic", jobfairContent.get("headPic"));
				}
			}

			Jobfair jobfair = jobFairService.getJobfair(fairId);
			if (jobfair != null && jobfair.getUrlType() == 1) {
				request.setAttribute("wapUrl", jobfair.getUrl());
			} else {
				request.setAttribute("wapUrl", String.format("%s/jobfair/fair/fairinfoopt!toJobFairWap.action?fairId=%d", request.getContextPath(), fairId));
			}

			return "/jobfair/jobfair.jsp";
		}
		return null;
	}

}
