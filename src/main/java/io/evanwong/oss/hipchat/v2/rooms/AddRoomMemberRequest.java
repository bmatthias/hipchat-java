package io.evanwong.oss.hipchat.v2.rooms;

import io.evanwong.oss.hipchat.v2.commons.NoContent;
import io.evanwong.oss.hipchat.v2.commons.PutRequest;
import org.apache.http.client.HttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AddRoomMemberRequest extends PutRequest<NoContent> {

    private final String roomIdOrName;
    private final String userIdOrEmail;

    public AddRoomMemberRequest(String userIdOrEmail, String roomIdOrName, String accessToken, HttpClient httpClient, ExecutorService executorService) {
        super(accessToken, httpClient, executorService);
        this.roomIdOrName = roomIdOrName;
        this.userIdOrEmail = userIdOrEmail;
    }

    public String getRoomIdOrName() {
        return roomIdOrName;
    }

    public String getUserIdOrEmail() {
        return userIdOrEmail;
    }

    @Override
    protected Map<String, Object> toQueryMap() {
        return new HashMap<>();
    }

    @Override
    protected String getPath() {
        return "/room/" + roomIdOrName + "/member/" + userIdOrEmail;
    }
}
