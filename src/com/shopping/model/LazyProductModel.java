package com.shopping.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
 
/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyProductModel extends LazyDataModel<Product> {
     
    private List<Product> datasource;
     
    public LazyProductModel(List<Product> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Product getRowData(String rowKey) {
        for(Product product : datasource) {
            if(Integer.toString(product.getId()).equals(rowKey))
                return product;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Product product) {
        return product.getId();
    }
 
    @Override
    public List<Product> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Product> data = new ArrayList<Product>();
 
        //filter
        for(Product product : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(product.getClass().getField(filterProperty).get(product));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(product);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
    

	public List<Product> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<Product> datasource) {
		this.datasource = datasource;
	}
}
