package com.icesi.botica.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class ScreenplayHooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());   // cada actor recibe su propio navegador
    }

    @ParameterType("[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+|él|ella")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);   // reconoce nombres y pronombres
    }
}
