package com.mindinventory.hiltarchitecturesample.data.entity

import com.google.gson.Gson

const val RANDOM_USER_JSON = "{\n" +
        "   \"results\":[{\n" +
        "         \"email\":\"austin.fuller@example.com\",\n" +
        "         \"picture\":{\n" +
        "            \"thumbnail\":\"https://randomuser.me/api/portraits/thumb/men/63.jpg\"\n" +
        "         },\n" +
        "         \"name\":{\n" +
        "            \"title\":\"Mr\",\n" +
        "            \"first\":\"Austin\",\n" +
        "            \"last\":\"Fuller\"\n" +
        "         }\n" +
        "      },\n" +
        "      {\n" +
        "         \"email\":\"kylie.gardner@example.com\",\n" +
        "         \"picture\":{\n" +
        "            \"thumbnail\":\"https://randomuser.me/api/portraits/thumb/women/21.jpg\"\n" +
        "         },\n" +
        "         \"name\":{\n" +
        "            \"title\":\"Mrs\",\n" +
        "            \"first\":\"Kylie\",\n" +
        "            \"last\":\"Gardner\"\n" +
        "         }\n" +
        "      }\n" +
        "   ]\n" +
        "}"

val gson = Gson()

fun randomUserResponse(): ResponseUsers =
    gson.fromJson<ResponseUsers>(RANDOM_USER_JSON, ResponseUsers::class.java)
