package fr.ostix.game.entity.component;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.ai.AICreator;
import fr.ostix.game.entity.component.animation.AnimationCreator;
import fr.ostix.game.entity.component.collision.CollisionCreator;
import fr.ostix.game.entity.component.light.LightCreator;
import fr.ostix.game.entity.component.particle.ParticleCreator;

public enum ComponentType {
    COLLISION_COMPONENT("Collision Component", new CollisionCreator(), 0),
    PARTICLE_COMPONENT("Particle Component", new ParticleCreator(), 7),
    AI_COMPONENT("AI Component", new AICreator(), 16),
    ANIMATED_COMPONENT("Animated Component", new AnimationCreator(), 0),
    LIGHT_COMPONENT("Light Component", new LightCreator(), 4);
    private final String name;
    private final ComponentCreator creator;
    private final int nbLine;

    ComponentType(String name, ComponentCreator creator, int nbLine) {
        this.name = name;
        this.creator = creator;
        this.nbLine = nbLine;
    }

    public Component createComponentToEntity(Entity e) {
        return this.creator.createComponent(e);
    }

    public Component loadComponent(Entity e, String component) {
        return this.creator.loadComponent(component, e);
    }

    public int getNbLine() {
        return nbLine;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
