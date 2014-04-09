package org.client.container1;

import java.util.List;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONArray;

public class ContainerEntryPoint implements EntryPoint {
	private static GameContainer gameContainer = null;
	
	@Override
	public void onModuleLoad() {
	  setListener();
	  gameContainer = new GameContainer();
	  
	  Button start = new Button("start");
	  start.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
	      gameContainer.gameStart();
	    }
	  });
	  
      Button refresh = new Button("refresh");
      refresh.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          gameContainer.updateGame();
        }
      });
      
      Button end = new Button("End");
      end.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          gameContainer.endGame();
        }
      });
      
      FlowPanel flowPanel = new FlowPanel();
      flowPanel.add(start);
      flowPanel.add(refresh);
      flowPanel.add(end);
      RootPanel.get("mainDiv").add(flowPanel);     
	}
	
	public static void messageListener(String message) {
	  JSONObject res = JSONParser.parseStrict(message).isObject();
	  if(res != null) {
	    String request = res.get("request").isString().stringValue();
	    switch (request) {
	      case "INSERT_NEW_MATCH": {
	    	//test("receive insert new match in container from controller");
	    	String gameId = res.get("gameId").isString().stringValue();
	    	List<String> playerIds = Lists.newArrayList();
	    	JSONArray playerIdsJSON = res.get("playerIds").isArray();
	    	for(int i=0;i<playerIdsJSON.size(); i++) {
	    	  playerIds.add(playerIdsJSON.get(i).isString().stringValue());
	    	}
	    	gameContainer.insertNewMatch(gameId, playerIds);
	    	break;
	      }
	      case "SEND_MAKE_MOVE": {
	    	//test("I have receive the initial move from controller, I'm container");
	    	gameContainer.sendMakeMove(res.get("move").isArray());
	        break;
	      }
	      default: {
	        throw new RuntimeException("No such Request");
	      }
	    }
	  }else {
	    throw new RuntimeException("JSON Parse Error!");
	  }
	}
	
	public native void setListener() /*-{
	  $wnd.addEventListener("message", function(event) {
	  	@org.client.container1.ContainerEntryPoint::messageListener(Ljava/lang/String;) (event.data);
	  },
	  false);
	}-*/;
	
	//the following code is used for testing
	private static native void test(String message) /*-{
	  alert(message);
	}-*/;
}
