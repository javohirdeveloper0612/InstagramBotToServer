package org.example.modul;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiModules {
    private JSONArray resources;
    private JSONArray edges;
    private JSONObject edge;
    private JSONObject node;
    private String typename;
    private String videoUrl;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public JSONArray getResources() {
        return resources;
    }

    public void setResources(JSONArray resources) {
        this.resources = resources;
    }

    public JSONArray getEdges() {
        return edges;
    }

    public void setEdges(JSONArray edges) {
        this.edges = edges;
    }

    public JSONObject getEdge() {
        return edge;
    }

    public void setEdge(JSONObject edge) {
        this.edge = edge;
    }

    public JSONObject getNode() {
        return node;
    }

    public void setNode(JSONObject node) {
        this.node = node;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
