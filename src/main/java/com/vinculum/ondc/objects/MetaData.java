package com.vinculum.ondc.objects;


import java.util.List;
import java.util.Map;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinculum.ondc.dao.objects.lookups.Access;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

public class MetaData {
	
	private SqlSessionTemplate session;
	private PlatformTransactionManager manager;
	private PoolingHttpClientConnectionManager connmanager;
	private ObjectMapper objectMapper;
	private ElasticsearchClient elasticsearchClient;
	private Map<String, DriverManagerDataSource> datasources;
	private ElasticsearchAsyncClient elasticsearchAsyncClient;
	private List<Access> infos;
	private Map<String, Object> context;
	
	
	
	public SqlSessionTemplate getSession() {
		return session;
	}
	public void setSession(SqlSessionTemplate session) {
		this.session = session;
	}
	public PlatformTransactionManager getManager() {
		return manager;
	}
	public void setManager(PlatformTransactionManager manager) {
		this.manager = manager;
	}
	
	public PoolingHttpClientConnectionManager getConnmanager() {
		return connmanager;
	}
	public void setConnmanager(PoolingHttpClientConnectionManager connmanager) {
		this.connmanager = connmanager;
	}
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	public void setObjectMapper(ObjectMapper mapper) {
		this.objectMapper = mapper;
	}
	public ElasticsearchClient getElasticsearchClient() {
		return elasticsearchClient;
	}
	public void setElasticsearchClient(ElasticsearchClient elasticsearchClient) {
		this.elasticsearchClient = elasticsearchClient;
	}
	public Map<String, DriverManagerDataSource> getDatasources() {
		return datasources;
	}
	public void setDatasources(Map<String, DriverManagerDataSource> datasources) {
		this.datasources = datasources;
	}
	public ElasticsearchAsyncClient getElasticsearchAsyncClient() {
		return elasticsearchAsyncClient;
	}
	public void setElasticsearchAsyncClient(ElasticsearchAsyncClient elasticsearchAsyncClient) {
		this.elasticsearchAsyncClient = elasticsearchAsyncClient;
	}
	public List<Access> getInfos() {
		return infos;
	}
	public void setInfos(List<Access> infos) {
		this.infos = infos;
	}
	public Map<String, Object> getContext() {
		return context;
	}
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	
	
}
