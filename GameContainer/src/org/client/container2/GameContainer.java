package org.client.container2;

import com.google.gwt.json.client.*;

public class GameContainer {  
  private final GameServer gameServer = new GameServer();
  //private String myPlayerId = "4812957531766784";
  //private String accessSignature = "ab0a72e65ac347f20160503ecd814dfa";
  
  private String myPlayerId = "6022506221666304";
  private String accessSignature = "e574f5726c00421bec1cfd05e7c5af5";
	
  public GameContainer() {
  }
  
  public void sendEnterQueue(JSONString gameId) {
    gameServer.sendEnterQueue(
    		this, 
    		new JSONString(accessSignature),
    		new JSONString(myPlayerId),
    		gameId); 
  }
  
  public void sendMakeMove(JSONArray move) {
	//test("send make move in container");
   	gameServer.sendMakeMove(move);
  }
  
  public void closeSocket() {
    gameServer.closeSocket();
  }
  
  public void updateUi(JSONObject state, JSONArray lastMove, JSONString lastMovePlayerId, JSONArray playerIds) {
    JSONObject message = new JSONObject();
    message.put("state", state);
    message.put("lastMove", lastMove);
    message.put("lastMovePlayerId", lastMovePlayerId);
    message.put("playerIds", playerIds);
    //test("I'm container, I will call controller to produce the initial move");
    sendMessage(message.toString());
  }
  
  public native void sendMessage(String message) /*-{
    var iframe = $doc.getElementById("child");
    iframe.contentWindow.postMessage(message, "*");
  }-*/;
  
  //Just for test
  private native void test(String message) /*-{
    $wnd.alert(message);
  }-*/;
}
