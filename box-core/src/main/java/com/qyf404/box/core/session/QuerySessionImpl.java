///*
// * JBoss, Home of Professional Open Source
// * Copyright 2005, JBoss Inc., and individual contributors as indicated
// * by the @authors tag. See the copyright.txt in the distribution for a
// * full listing of individual contributors.
// *
// * This is free software; you can redistribute it and/or modify it
// * under the terms of the GNU Lesser General Public License as
// * published by the Free Software Foundation; either version 2.1 of
// * the License, or (at your option) any later version.
// *
// * This software is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// * Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public
// * License along with this software; if not, write to the Free
// * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
// * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
// */
//package com.qyf404.box.core.session;
//
//import java.io.Serializable;
//import java.security.acl.Group;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.hibernate.Query;
//import org.hibernate.Session;
//
//import com.qyf404.box.core.BoxException;
//import com.qyf404.box.core.env.EnvironmentImpl;
//import com.qyf404.box.core.query.QueryCondition;
//import com.qyf404.box.core.query.QueryOperator;
//import com.qyf404.box.core.query.QueryProperty;
//
///**
// * query session implementation for hibernate.
// *
// */
//public class QuerySessionImpl implements QuerySession {
//
//    private static Log log = Log.getLog(QuerySessionImpl.class.getName());
//
//    protected Session session;
//
//    public Object query(QueryProperty queryProperty) {
//        if (DeploymentImpl.class.getName().equals(queryProperty.getKey())
//                || JobImpl.class.getName().equals(queryProperty.getKey())
//                || MessageImpl.class.getName().equals(queryProperty.getKey())
//                || TimerImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryDefault(queryProperty);
//        } else if (ExecutionImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryProcessInstance(queryProperty);
//        } else if (TaskImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryTask(queryProperty);
//        } else if (ProcessDefinitionImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryProcessDefinition(queryProperty);
//        } else if (HistoryActivityInstanceImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryHistoryActivityInstance(queryProperty);
//        } else if (HistoryDetailImpl.class.getName().equals(queryProperty.getKey())
//                || HistoryCommentImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryHistoryDetail(queryProperty);
//        } else if (HistoryProcessInstanceImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryHistoryProcessInstance(queryProperty);
//        } else if (HistoryTaskImpl.class.getName().equals(queryProperty.getKey())) {
//            return queryHistoryTask(queryProperty);
//        }
//        log.error("cannot handle [" + queryProperty.getKey() + "]");
//        return null;
//    }
//
//    protected Object queryDefault(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(*) from ")
//                .append(key);
//        } else {
//            buff.append("from ")
//                .append(key);
//        }
//
//        // where
//        Map<String, Object> params = new HashMap<String, Object>();
//        appendWhereClause(buff, queryProperty, params, false);
//
//        // order by
//        appendOrderClause(buff, queryProperty);
//
//        return executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryProcessInstance(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(distinct e) from ")
//                .append(key)
//                .append(" e");
//        } else {
//            buff.append("select distinct e from ")
//                .append(key)
//                .append(" e");
//        }
//
//        // where
//        Map params = new HashMap();
//        boolean isAlreadyAddWhere = false;
//        if (queryProperty.hasCondition("processDefinitionKey")) {
//            QueryCondition condition = queryProperty.getCondition("processDefinitionKey");
//            Object processDefinitionKey = condition.getValue();
//            buff.append(", ")
//                .append(DeploymentProperty.class.getName())
//                .append(" as idProperty, ")
//                .append(DeploymentProperty.class.getName())
//                .append(" as keyProperty ")
//                .append(" where idProperty.objectName = keyProperty.objectName ")
//                .append(" and idProperty.deployment = keyProperty.deployment ")
//                .append(" and idProperty.stringValue = e.processDefinitionId ")
//                .append(" and keyProperty.stringValue ")
//                .append(condition.getOperator());
//            if (condition.getOperator() == QueryOperator.IN || condition.getOperator() == QueryOperator.NOT_IN) {
//                buff.append(" (:processDefinitionKey) ");
//            } else {
//                buff.append(" :processDefinitionKey ");
//            }
//
//            params.put("processDefinitionKey", processDefinitionKey);
//
//            isAlreadyAddWhere = true;
//            queryProperty.removeCondition("processDefinitionKey");
//        }
//        this.appendWhereClause(buff, queryProperty, params, isAlreadyAddWhere);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryTask(QueryProperty queryProperty) {
//        if (queryProperty.hasCondition("candidate")) {
//            return queryTaskWithParticipant(queryProperty);
//        } else {
//            return queryTaskWithoutParticipant(queryProperty);
//        }
//    }
//
//    protected Object queryTaskWithParticipant(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(distinct task.id) from ")
//                .append(ParticipationImpl.class.getName())
//                .append(" as participant join participant.task as task ");
//        } else {
//            buff.append("select distinct task.id from ")
//                .append(ParticipationImpl.class.getName())
//                .append(" as participant join participant.task as task ");
//        }
//        buff.append("where participant.type = 'candidate'");
//
//        IdentitySession identitySession = EnvironmentImpl.getFromCurrent(IdentitySession.class);
//
//        String candidate = (String) queryProperty.getCondition("candidate").getValue();
//        List<Group> groups = identitySession.findGroupsByUser(candidate);
//
//        if (log.isDebugEnabled()) {
//            log.debug("setting parameter candidateUserId: " + candidate);
//        }
//
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        if (groups.isEmpty()) {
//            buff.append(" and participant.userId = :candidateUserId ");
//            params.put("candidateUserId", candidate);
//        } else {
//            List<String> groupIds = new ArrayList<String>();
//            for (Group group : groups) {
//              groupIds.add(group.getId());
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("setting parameter candidateGroupIds: " + groupIds);
//            }
//
//            buff.append(" and (participant.userId = :candidateUserId or participant.groupId in (:candidateGroupIds))");
//            params.put("candidateUserId", candidate);
//            params.put("candidateGroupIds", groupIds);
//        }
//
//        queryProperty.removeCondition("candidate");
//
//        this.appendWhereClause(buff, queryProperty, params, true);
//
//        Object result = this.executeQuery(buff.toString(), params, queryProperty);
//
//        // in order to solve duplicated records in oracle.
//
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_UNIQUE) {
//            if (result == null) {
//                return null;
//            } else {
//                return session.get(TaskImpl.class, (Serializable) result);
//            }
//        } else if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            return result;
//        }
//
//        List<Long> taskIds = CollectionUtil.checkList((List<?>) result, Long.class);
//        if (taskIds.isEmpty()) {
//            return Collections.EMPTY_LIST;
//        }
//
//        buff = new StringBuilder();
//
//        buff.append("from ")
//            .append(TaskImpl.class.getName())
//            .append(" task where task.id in (:taskIds)");
//
//        this.appendOrderClause(buff, queryProperty);
//
//        return session.createQuery(buff.toString())
//            .setParameterList("taskIds", taskIds)
//            .list();
//    }
//
//    protected Object queryTaskWithoutParticipant(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(task.id) from ")
//                .append(TaskImpl.class.getName())
//                .append(" as task ");
//        } else {
//            buff.append("from ")
//                .append(TaskImpl.class.getName())
//                .append(" as task ");
//        }
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        this.appendWhereClause(buff, queryProperty, params, false);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryProcessDefinition(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//
//        // select, from
//        buff.append("select new map(idProperty.objectName as objectName,")
//            .append("idProperty.deployment.dbid as deploymentDbid) ")
//            .append("from ")
//            .append(DeploymentImpl.class.getName())
//            .append(" as deployment, ")
//            .append(DeploymentProperty.class.getName())
//            .append(" as idProperty, ")
//            .append(DeploymentProperty.class.getName())
//            .append(" as keyProperty, ")
//            .append(DeploymentProperty.class.getName())
//            .append(" as versionProperty ");
//
//        // where
//        buff.append("where idProperty.key = '" + DeploymentImpl.KEY_PROCESS_DEFINITION_ID + "' ")
//            .append("and idProperty.deployment = deployment ")
//            .append("and keyProperty.key = '" + DeploymentImpl.KEY_PROCESS_DEFINITION_KEY + "' ")
//            .append("and keyProperty.objectName = idProperty.objectName ")
//            .append("and keyProperty.deployment = deployment ")
//            .append("and versionProperty.key = '" + DeploymentImpl.KEY_PROCESS_DEFINITION_VERSION + "' ")
//            .append("and versionProperty.objectName = idProperty.objectName ")
//            .append("and versionProperty.deployment = deployment ");
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        this.appendWhereClause(buff, queryProperty, params, true);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        String hql = buff.toString();
//        log.debug(hql);
//
//        Query query = session.createQuery(hql).setProperties(params);
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE_UNIQUE
//                || queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE_COUNT) {
//             query.setFirstResult(queryProperty.getFirstResult())
//                  .setMaxResults(queryProperty.getMaxResults());
//        }
//
//        RepositorySession repositorySession = EnvironmentImpl.getFromCurrent(RepositorySession.class);
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_UNIQUE
//                || queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE_UNIQUE) {
//            Object result = query.uniqueResult();
//            if (result == null) return null;
//            return getProcessDefinition(repositorySession, (Map<?, ?>) result);
//        } else {
//            List<?> result = query.list();;
//            if (result.isEmpty()) return Collections.EMPTY_LIST;
//
//            List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
//            for (Map<?, ?> propertyMap : CollectionUtil.checkList(result, Map.class)) {
//                ProcessDefinitionImpl processDefinition =
//                  getProcessDefinition(repositorySession, propertyMap);
//                processDefinitions.add(processDefinition);
//            }
//            return processDefinitions;
//        }
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryHistoryActivityInstance(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(*) from ")
//                .append(key);
//        } else {
//            buff.append("from ")
//                .append(key);
//        }
//
//        // where
//        Map<String, Object> params = new HashMap<String, Object>();
//        boolean alreadyAddWhere = false;
//
//        if (queryProperty.hasCondition("tookLessThen")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("duration<:tookLessThen");
//            params.put("tookLessThen", queryProperty.getCondition("tookLessThen").getValue());
//            queryProperty.removeCondition("tookLessThen");
//        }
//
//        if (queryProperty.hasCondition("tookLongerThen")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("duration>:tookLongerThen");
//            params.put("tookLongerThen", queryProperty.getCondition("tookLongerThen").getValue());
//            queryProperty.removeCondition("tookLongerThen");
//        }
//
//        if (queryProperty.hasCondition("startedBefore")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("startTime<:startedBefore");
//            params.put("startedBefore", queryProperty.getCondition("startedBefore").getValue());
//            queryProperty.removeCondition("startedBefore");
//        }
//
//        if (queryProperty.hasCondition("startedAfter")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("startTime>:startedAfter");
//            params.put("startedAfter", queryProperty.getCondition("startedAfter").getValue());
//            queryProperty.removeCondition("startedAfter");
//        }
//
//        this.appendWhereClause(buff, queryProperty, params, alreadyAddWhere);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryHistoryDetail(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(*) from ")
//                .append(key);
//        } else {
//            buff.append("from ")
//                .append(key);
//        }
//
//        // where
//        Map<String, Object> params = new HashMap<String, Object>();
//        boolean alreadyAddWhere = false;
//
//        if (queryProperty.hasCondition("timeBefore")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("startTime<:timeBefore");
//            params.put("timeBefore", queryProperty.getCondition("timeBefore").getValue());
//            queryProperty.removeCondition("timeBefore");
//        }
//
//        if (queryProperty.hasCondition("timeAfter")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("startTime>:timeAfter");
//            params.put("timeAfter", queryProperty.getCondition("timeAfter").getValue());
//            queryProperty.removeCondition("timeAfter");
//        }
//
//        this.appendWhereClause(buff, queryProperty, params, alreadyAddWhere);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryHistoryProcessInstance(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(*) from ")
//                .append(key);
//        } else {
//            buff.append("from ")
//                .append(key);
//        }
//
//        // where
//        Map<String, Object> params = new HashMap<String, Object>();
//        boolean alreadyAddWhere = false;
//
//        if (queryProperty.hasCondition("endedBefore")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("endTime<:endedBefore");
//            params.put("endedBefore", queryProperty.getCondition("endedBefore").getValue());
//            queryProperty.removeCondition("endedBefore");
//        }
//
//        if (queryProperty.hasCondition("endedAfter")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("endTime>=:endedAfter");
//            params.put("endedAfter", queryProperty.getCondition("endedAfter").getValue());
//            queryProperty.removeCondition("endedAfter");
//        }
//
//        this.appendWhereClause(buff, queryProperty, params, alreadyAddWhere);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected Object queryHistoryTask(QueryProperty queryProperty) {
//        StringBuilder buff = new StringBuilder();
//        String key = queryProperty.getKey();
//
//        // select, from
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT) {
//            buff.append("select count(ht) from ")
//                .append(key)
//                .append(" as ht");
//        } else {
//            buff.append("select ht from ")
//                .append(key)
//                .append(" as ht");
//        }
//
//        // where
//        Map<String, Object> params = new HashMap<String, Object>();
//        boolean alreadyAddWhere = false;
//
//        if (queryProperty.hasCondition("processInstanceId")) {
//            buff.append(", ")
//                .append(HistoryTaskInstanceImpl.class.getName())
//                .append(" as hti ")
//                .append(" where ht=hti.historyTask")
//                .append(" and hti.historyProcessInstance.processInstanceId=:processInstanceId");
//            alreadyAddWhere = true;
//            params.put("processInstanceId", queryProperty.getCondition("processInstanceId").getValue());
//            queryProperty.removeCondition("processInstanceId");
//        }
//
//        if (queryProperty.hasCondition("tookLessThen")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.duration<:tookLessThen");
//            params.put("tookLessThen", queryProperty.getCondition("tookLessThen").getValue());
//            queryProperty.removeCondition("tookLessThen");
//        }
//
//        if (queryProperty.hasCondition("tookLongerThen")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.duration>:tookLongerThen");
//            params.put("tookLongerThen", queryProperty.getCondition("tookLongerThen").getValue());
//            queryProperty.removeCondition("tookLongerThen");
//        }
//
//        if (queryProperty.hasCondition("startedBefore")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.createTime<:startedBefore");
//            params.put("startedBefore", queryProperty.getCondition("startedBefore").getValue());
//            queryProperty.removeCondition("startedBefore");
//        }
//
//        if (queryProperty.hasCondition("startedAfter")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.createTime>:startedAfter");
//            params.put("startedAfter", queryProperty.getCondition("startedAfter").getValue());
//            queryProperty.removeCondition("startedAfter");
//        }
//
//        if (queryProperty.hasCondition("endedBefore")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.endTime<:endedBefore");
//            params.put("endedBefore", queryProperty.getCondition("endedBefore").getValue());
//            queryProperty.removeCondition("endedBefore");
//        }
//
//        if (queryProperty.hasCondition("endedAfter")) {
//            if (alreadyAddWhere) {
//                buff.append(" and ");
//            } else {
//                buff.append(" where ");
//                alreadyAddWhere = true;
//            }
//            buff.append("ht.endTime>:endedAfter");
//            params.put("endedAfter", queryProperty.getCondition("endedAfter").getValue());
//            queryProperty.removeCondition("endedAfter");
//        }
//
//        this.appendWhereClause(buff, queryProperty, params, alreadyAddWhere);
//
//        // order by
//        this.appendOrderClause(buff, queryProperty);
//
//        return this.executeQuery(buff.toString(), params, queryProperty);
//    }
//
//    // ~ ======================================================================
//
//    protected void appendWhereClause(StringBuilder buff, QueryProperty queryProperty,
//      Map<String, Object> params, boolean originalAlreadyAddWhere) {
//        // where
//        boolean alreadyAddWhere = originalAlreadyAddWhere;
//        for (Map.Entry<String, QueryCondition> entry : queryProperty.getConditions().entrySet()) {
//            QueryCondition queryCondition = entry.getValue();
//            if (!alreadyAddWhere) {
//                alreadyAddWhere = true;
//                buff.append(" where ");
//            } else {
//                buff.append(" and ");
//            }
//
//            String fieldName = entry.getKey();
//            String varName = fieldName.replaceAll("\\.", "_");
//            buff.append(fieldName)
//                .append(" ")
//                .append(queryCondition.getOperator());
//
//            if (queryCondition.getOperator() == QueryOperator.IN
//                    || queryCondition.getOperator() == QueryOperator.NOT_IN) {
//                buff.append("(:")
//                    .append(varName)
//                    .append(")");
//                params.put(varName, queryCondition.getValue());
//            } else if (queryCondition.getOperator() == QueryOperator.IS_NULL
//                    || queryCondition.getOperator() == QueryOperator.NOT_NULL) {
//            } else {
//                buff.append(" :")
//                    .append(varName);
//                params.put(varName, queryCondition.getValue());
//            }
//        }
//    }
//
//    protected void appendOrderClause(StringBuilder buff, QueryProperty queryProperty) {
//        // order by
//        boolean alreadyAddOrder = false;
//        for (Map.Entry<String, String> entry : queryProperty.getOrders().entrySet()) {
//            if (!alreadyAddOrder) {
//                alreadyAddOrder = true;
//                buff.append(" order by ");
//            } else {
//                buff.append(",");
//            }
//
//            buff.append(entry.getKey())
//                .append(" ")
//                .append(entry.getValue());
//        }
//    }
//
//    protected Object executeQuery(String hql, Map<String, Object> params, QueryProperty queryProperty) {
//        log.debug(hql);
//
//        Query query = session.createQuery(hql);
//        query.setProperties(params);
//
//        if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE) {
//             return query.setFirstResult(queryProperty.getFirstResult())
//                         .setMaxResults(queryProperty.getMaxResults())
//                         .list();
//        } else if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE_UNIQUE
//                || queryProperty.getResultType() == QueryProperty.RESULT_TYPE_PAGE_COUNT) {
//             return query.setFirstResult(queryProperty.getFirstResult())
//                         .setMaxResults(queryProperty.getMaxResults())
//                         .uniqueResult();
//        } else if (queryProperty.getResultType() == QueryProperty.RESULT_TYPE_COUNT
//                || queryProperty.getResultType() == QueryProperty.RESULT_TYPE_UNIQUE) {
//            return query.uniqueResult();
//        } else {
//            return query.list();
//        }
//    }
//
//    private static ProcessDefinitionImpl getProcessDefinition(RepositorySession repositorySession,
//      Map<?, ?> propertyMap) {
//        String deploymentId = propertyMap.get("deploymentDbid").toString();
//        String objectName = (String)propertyMap.get("objectName");
//        return (ProcessDefinitionImpl) repositorySession.getObject(deploymentId, objectName);
//    }
//
//    // ~ ======================================================================
//
//    public Object  execute(String queryString,
//                           String queryType,
//                           Map<String, Object> params,
//                           boolean isUpdate,
//                           boolean isUnique) {
//        Query query = null;
//
//        if ("sql".equalsIgnoreCase(queryType)) {
//            query = session.createSQLQuery(queryString);
//        } else if ("hql".equalsIgnoreCase(queryType)) {
//            query = session.createQuery(queryString);
//        } else {
//            throw new BoxException("Unsupported query type : " + queryString);
//        }
//
//        query.setProperties(params);
//
//        if (isUpdate) {
//            return query.executeUpdate();
//        } else if (isUnique) {
//            return query.uniqueResult();
//        } else {
//            return query.list();
//        }
//    }
//}
