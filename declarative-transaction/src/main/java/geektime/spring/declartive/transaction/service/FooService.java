package geektime.spring.declartive.transaction.service;

import geektime.spring.declartive.transaction.exception.RollbackException;

public interface FooService {

    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;

    void invokeInsertThenRollbackBySelfService() throws RollbackException;

    void invokeInsertThenRollbackByAopContext() throws RollbackException;

    void invokeInsertThenRollbackAddTransactional() throws RollbackException;
}
