package com.example.springBatch.Dao;

import org.springframework.batch.item.ItemProcessor;

import com.example.springBatch.Entities.Orginizations;

public class OrgProcessor implements ItemProcessor<Orginizations,Orginizations>{

	@Override
	public Orginizations process(Orginizations o) throws Exception {
		// TODO Auto-generated method stub
		return o;
	}

}
