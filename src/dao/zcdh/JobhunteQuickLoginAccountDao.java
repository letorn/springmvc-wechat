package dao.zcdh;

import model.zcdh.JobhunteQuickLoginAccount;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("jobhunteQuickLoginAccountDao")
public class JobhunteQuickLoginAccountDao extends Store<JobhunteQuickLoginAccount> {

	public JobhunteQuickLoginAccount get(String type, String warrantyId) {
		return select("select * from zcdh_jobhunte_quick_login_account where type=? and warranty_id=?", new Object[] { type, warrantyId });
	}

	public Boolean delete(String type, String warrantyId) {
		return delete("delete from zcdh_jobhunte_quick_login_account where type=? and warranty_id=?", new Object[] { type, warrantyId });
	}

}
