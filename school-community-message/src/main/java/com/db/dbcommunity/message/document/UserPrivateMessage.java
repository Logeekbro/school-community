package com.db.dbcommunity.message.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("user-private-messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserPrivateMessage extends UserMessage {

    private Long senderId;

    private Long targetId;

    private String content;


}
