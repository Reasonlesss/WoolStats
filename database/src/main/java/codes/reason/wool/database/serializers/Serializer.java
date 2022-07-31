package codes.reason.wool.database.serializers;

import org.bson.Document;

public interface Serializer<T> {

    T fromDocument(Document document);

    Document toDocument(T t);

}
