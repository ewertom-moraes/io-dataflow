package br.com.tomcode.iodataflow.api;

import java.util.ArrayList;
import java.util.List;

public class DataFlowRecord<T> {
	
	private List<T> objects;
	private Object layout;

	public DataFlowRecord(T object, Object layout) {
		this.objects = new ArrayList<T>();
		this.objects.add(object);
		this.layout = layout;
	}
	
	public DataFlowRecord(List<T> objects) {
		this.objects = objects;
	}

	public List<T> getObjects() {
		return objects;
	}

	public Object getLayout() {
		return layout;
	}

	
	
	
}
