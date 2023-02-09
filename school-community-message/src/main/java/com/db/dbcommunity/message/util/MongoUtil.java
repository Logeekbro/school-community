package com.db.dbcommunity.message.util;

import org.springframework.data.mongodb.core.query.Criteria;

public class MongoUtil {

    public static Criteria notDeletedCriteria() {
        return Criteria.where("deleted").is(false);
    }
}
