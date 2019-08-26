package geektime.spring.declarative.transaction.propagation.service;

import geektime.spring.declarative.transaction.propagation.exception.RollbackException;

public interface FooService {

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback();

}
