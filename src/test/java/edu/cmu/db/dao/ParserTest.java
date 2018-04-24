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
//    private Parser parser;

    @Before
    public void setUp() {
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
//        this.parser = new Parser(conversationDAO, messageDAO, result);
    }

    @Test
    public void parseProfile() {
//        try {
//            // unzip and parse
//            parser.parseProfile(inputStream);
//        } catch (IOException e) {
//            // if parse failed
//            e.printStackTrace();
//            resultDAO.deleteResultByID(result.getResultID());
//        }
    }
}