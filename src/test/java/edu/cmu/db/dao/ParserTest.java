package edu.cmu.db.dao;

import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Result;
import io.dropwizard.testing.junit.DAOTestRule;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ParserTest {
    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Conversation.class).build();

    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;
    private ResultDAO resultDAO;
    private Result result;
    private InputStream inputStream;
    private Parser parser;

    @Before
    public void setUp() {
//        SessionFactory sessionFactory = new SessionFactory() {
//            @Override
//            public SessionFactoryOptions getSessionFactoryOptions() {
//                return null;
//            }
//
//            @Override
//            public SessionBuilder withOptions() {
//                return null;
//            }
//
//            @Override
//            public Session openSession() throws HibernateException {
//                return null;
//            }
//
//            @Override
//            public Session getCurrentSession() throws HibernateException {
//                return null;
//            }
//
//            @Override
//            public StatelessSessionBuilder withStatelessOptions() {
//                return null;
//            }
//
//            @Override
//            public StatelessSession openStatelessSession() {
//                return null;
//            }
//
//            @Override
//            public StatelessSession openStatelessSession(Connection connection) {
//                return null;
//            }
//
//            @Override
//            public Statistics getStatistics() {
//                return null;
//            }
//
//            @Override
//            public void close() throws HibernateException {
//
//            }
//
//            @Override
//            public boolean isClosed() {
//                return false;
//            }
//
//            @Override
//            public Cache getCache() {
//                return null;
//            }
//
//            @Override
//            public Set getDefinedFilterNames() {
//                return null;
//            }
//
//            @Override
//            public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
//                return null;
//            }
//
//            @Override
//            public boolean containsFetchProfileDefinition(String name) {
//                return false;
//            }
//
//            @Override
//            public TypeHelper getTypeHelper() {
//                return null;
//            }
//
//            @Override
//            public ClassMetadata getClassMetadata(Class entityClass) {
//                return null;
//            }
//
//            @Override
//            public ClassMetadata getClassMetadata(String entityName) {
//                return null;
//            }
//
//            @Override
//            public CollectionMetadata getCollectionMetadata(String roleName) {
//                return null;
//            }
//
//            @Override
//            public Map<String, ClassMetadata> getAllClassMetadata() {
//                return null;
//            }
//
//            @Override
//            public Map getAllCollectionMetadata() {
//                return null;
//            }
//
//            @Override
//            public Reference getReference() throws NamingException {
//                return null;
//            }
//
//            @Override
//            public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
//                return null;
//            }
//
//            @Override
//            public Metamodel getMetamodel() {
//                return null;
//            }
//
//            @Override
//            public EntityManager createEntityManager() {
//                return null;
//            }
//
//            @Override
//            public EntityManager createEntityManager(Map map) {
//                return null;
//            }
//
//            @Override
//            public EntityManager createEntityManager(SynchronizationType synchronizationType) {
//                return null;
//            }
//
//            @Override
//            public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
//                return null;
//            }
//
//            @Override
//            public CriteriaBuilder getCriteriaBuilder() {
//                return null;
//            }
//
//            @Override
//            public boolean isOpen() {
//                return false;
//            }
//
//            @Override
//            public Map<String, Object> getProperties() {
//                return null;
//            }
//
//            @Override
//            public PersistenceUnitUtil getPersistenceUnitUtil() {
//                return null;
//            }
//
//            @Override
//            public void addNamedQuery(String name, Query query) {
//
//            }
//
//            @Override
//            public <T> T unwrap(Class<T> cls) {
//                return null;
//            }
//
//            @Override
//            public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
//
//            }
//        };
        SessionFactory sessionFactory = database.getSessionFactory();
        this.conversationDAO = new ConversationDAO(sessionFactory);
        this.messageDAO = new MessageDAO(sessionFactory);
        this.result = new Result();
        result.setRetentionID(1);
        result = resultDAO.persistNewResult(result);
        try {
            inputStream = new FileInputStream("src/test/resources/SomeonesData.zip");
        } catch (FileNotFoundException e) {
            System.out.println("Read zip file error");
            e.printStackTrace();
        }
        this.parser = new Parser(conversationDAO, messageDAO, result);
    }

    @Test
    public void parseProfile() {
        try {
            // unzip and parse
            parser.parseProfile(inputStream);
        } catch (IOException e) {
            // if parse failed
            e.printStackTrace();
            resultDAO.deleteResultByID(result.getResultID());
        }
    }

//    @Test
//    public void getDestinationPath() {
//    }

//    @Test
//    public void deleteFileOrFolder() {
//
//    }
}