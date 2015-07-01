package dao.zcdh;

import model.zcdh.JobfairPropaganda;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("jobfairPropagandaDao")
public class JobfairPropagandaDao extends Store<JobfairPropaganda> {

	public JobfairPropaganda getByFairId(Long fairId) {
		return select("select * from zcdh_jobfair_propaganda where fair_id=?", new Object[] { fairId });
	}

}
