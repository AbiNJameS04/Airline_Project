package com.spring.hackathon.repository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.spring.hackathon.entity.Airport;

@Component
public class SearchRepositoryImpl implements SearchRepository {

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Airport> findByText(String text) {
        // TODO Auto-generated method stub

        // MongoDatabase database = client.getDatabase("airbookdb");

    //     MongoCollection<Document> collection = database.getCollection("airports");
    //     // AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new
    //     // Document("$search",
    //     // new Document("index", "airportIndex")
    //     // .append("text",
    //     // new Document("query", text)
    //     // .append("path", "name")))));
    //     AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
    //             new Document("index", "airportIndex")
    //                     .append("text",
    //                             new Document("query", text)
    //                                     .append("path", Arrays.asList("name", "iata_code", "icao_code"))))));

    //     final List<Airport> airports = new ArrayList<>();

    //     result.forEach(doc -> airports.add(converter.read(Airport.class, doc)));
    //     return airports;
    // }


    MongoDatabase database = client.getDatabase("airbookdb");
MongoCollection<Document> collection = database.getCollection("airports");
AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
    new Document("index", "airportIn")
            .append("text", 
    new Document("query", text)
                .append("path", Arrays.asList("name", "iataCode", "cityName"))))));
                final List<Airport> airports = new ArrayList<>();

        result.forEach(doc -> airports.add(converter.read(Airport.class, doc)));
        return airports;
}
}