package org.hibernate.dialect;

import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class MySQL5CustomDialect extends MySQL5Dialect {
    public MySQL5CustomDialect() {
        super();
        registerHibernateType(Types.BIGINT, StandardBasicTypes.LONG.getName());
    }
}
