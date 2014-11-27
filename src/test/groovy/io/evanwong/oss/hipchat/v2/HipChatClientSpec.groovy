package io.evanwong.oss.hipchat.v2

import io.evanwong.oss.hipchat.v2.rooms.MessageColor
import io.evanwong.oss.hipchat.v2.rooms.MessageFormat
import io.evanwong.oss.hipchat.v2.rooms.Privacy
import spock.lang.Specification

class HipChatClientSpec extends Specification {
    def client = new HipChatClient()
    //fake token
    def token = "fasdfasdfasdfas123123"

    def setupSpec() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")
    }

    def "prepareGetAllRoomsRequestBuilder should create a GetAllRoomsRequest properly"() {
        setup:
        def builder = client.prepareGetAllRoomsRequestBuilder(token)
        builder = builder.setIncludeArchived(includeArchived)
        builder = builder.setMaxResults(maxResults)
        builder = builder.setStartIndex(startIndex)
        expansions.each {
            builder = builder.addExpansion(it)
        }

        when:
        def req = builder.build()

        then:
        req.startIndex == startIndex
        req.maxResults == maxResults
        req.includeArchived == includeArchived
        req.expansions.every { expansions.contains(it) }


        where:
        includeArchived | maxResults | startIndex | expansions
        true            | 123        | 456        | ["title1", "title2"]
        false           | 321        | 654        | ["title1", "title2"]
        null            | null       | null       | ["title1"]
        true            | null       | 456        | null

    }

    def "prepareSendRoomNotificationRequestBuilder should create a SendRoomNotificationRequest properly"() {
        setup:
        def builder = client.prepareSendRoomNotificationRequestBuilder(room, message, token)
        builder.color = color
        builder.notify = notify
        builder.messageFormat = messageFormat

        when:
        def req = builder.build()

        then:
        req.idOrName == room
        req.color == color
        req.message == message
        req.notify == notify
        req.messageFormat == messageFormat


        where:
        room      | message        | color               | notify | messageFormat
        "room1"   | "test message" | MessageColor.YELLOW | true   | MessageFormat.TEXT
        "room321" | "new message"  | MessageColor.GREEN  | false  | MessageFormat.HTML

    }

    def "prepareCreateRoomRequestBuilder should create a CreateRoomRequest properly"() {
        setup:
        def builder = client.prepareCreateRoomRequestBuilder(name, token)
        builder.guestAcccess = guestAccess
        builder.ownerUserId = ownerUserId
        builder.privacy = privacy
        builder.topic = topic

        when:
        def req = builder.build()

        then:
        req.name == name
        req.ownerUserId == ownerUserId
        req.privacy == privacy
        req.guestAcccess == guestAccess
        req.topic == topic


        where:
        name       | ownerUserId | privacy         | guestAccess | topic
        "room1"    | "@testuser" | Privacy.PRIVATE | true        | "topic1"
        "room321"  | "user"      | Privacy.PUBLIC  | false       | null
        "room456"  | "user"      | Privacy.PRIVATE | false       | null
        "adsfasdf" | "user123"   | null            | null        | "topic2"
    }

    def "prepareGetRoomRequestBuilder should create a GetRoomRequest properly"() {
        setup:
        def builder = client.prepareGetRoomRequestBuilder(name, token)

        when:
        def req = builder.build()

        then:
        req.roomIdOrName == name

        where:
        name    | _
        "test1" | _
        "1test" | _
    }

}