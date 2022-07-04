package com.apet2929.game.engine.box2d;

import com.apet2929.game.engine.box2d.entity.Material;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {
    private World world;

    public BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory instance;
    public static BodyFactory getInstance(World world){
        if(instance == null){
            return new BodyFactory(world);
        }
        return instance;
    }

    public Body makeSensorRectBody(float x, float y, float width, float height){
        BodyDef bodyDef = createBodyDef(x, y, BodyDef.BodyType.StaticBody, true);

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        Fixture fixture = body.createFixture(Material.WOOD.makeFixture(shape));
        fixture.setSensor(true);
        shape.dispose();
        return body;
    }


    public Body makePolygonShapeBody(Vector2[] vertices, float x, float y, Material material, BodyDef.BodyType bodyType){
        BodyDef boxBodyDef = createBodyDef(x, y, bodyType, false);

        Body boxBody = world.createBody(boxBodyDef);


        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);

        boxBody.createFixture(material.makeFixture(polygon));

        polygon.dispose();


        return boxBody;
    }

    public Body makeRectBody(float x, float y, float width, float height, Material material, BodyDef.BodyType bodyType){
        return makeRectBody(x, y, width, height, material, bodyType, false);
    }
    public Body makeRectBody(float x, float y, float width, float height, Material material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef bodyDef = createBodyDef(x, y, bodyType, fixedRotation);

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        body.createFixture(material.makeFixture(shape));
        shape.dispose();
        return body;
    }

    public Body makeCircleBody(float x, float y, float radius, Material material, BodyDef.BodyType bodyType){
        return makeCircleBody(x, y, radius, material, bodyType, false);
    }

    public Body makeCircleBody(float x, float y, float radius, Material material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef bodyDef = createBodyDef(x, y, bodyType, fixedRotation);

        //create the body to attach said definition
        Body body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius /2);
        body.createFixture(material.makeFixture(circleShape));
        circleShape.dispose();
        return body;
    }

    public BodyDef createBodyDef(float x, float y, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = x;
        bodyDef.position.y = y;
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.type = bodyType;
        return bodyDef;
    }

}
