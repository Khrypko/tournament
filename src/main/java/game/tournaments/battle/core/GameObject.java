package game.tournaments.battle.core;

import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.component.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public class GameObject {

    private long id;
    private Map<String, Component> components = new HashMap<>();
    private long owner;

    public GameObject(long id, long owner) {
        this.id = id;
        this.owner = owner;
    }

    public GameObject(Map<String, Component> components, long id, long owner) {
        this.components = components;
        this.id = id;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public long getOwner() {
        return owner;
    }

    public void setComponents(Map<String, Component> components) {
        this.components = components;
    }

    public Map<String, Component> getComponents() {
        return components;
    }

    public Component getComponent(String id){
        return components.get(id);
    }

    public void addComponent(Component component){
        this.components.put(component.getId(), component);
    }

    public void removeComponent(String componentId){
        this.components.remove(componentId);
    }
}
