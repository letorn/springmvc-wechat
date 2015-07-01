package dao;

import java.util.ArrayList;
import java.util.List;

import model.Chatter;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("chatterDao")
public class ChatterDao extends Store<Chatter> {

	public List<Chatter> findAll() {
		return new ArrayList<Chatter>(Stack.chatterIdMap.values());
	}

	public Chatter getByOpenId(String openId) {
		return Stack.chatterOpenIdMap.get(openId);
	}

	public Boolean add(Chatter chatter) {
		if (super.add(chatter)) {
			if (chatter.getId() != null)
				Stack.chatterIdMap.put(chatter.getId(), chatter);
			if (chatter.getOpenId() != null)
				Stack.chatterOpenIdMap.put(chatter.getOpenId(), chatter);
			return true;
		}
		return false;
	}

	public Boolean update(Chatter chatter) {
		if (super.update(chatter)) {
			if (chatter.getId() != null)
				Stack.chatterIdMap.put(chatter.getId(), chatter);
			if (chatter.getOpenId() != null)
				Stack.chatterOpenIdMap.put(chatter.getOpenId(), chatter);
			return true;
		}
		return false;
	}

}
