package dev.future.testapp.speedametr;

import android.location.Location;

public class CLocation extends Location {

    public boolean bUsMatericUnits = false;

    public CLocation(Location location){
        this(location,true);
    }


    public CLocation(Location location, boolean bUsMatericUnits) {
        super(location);
        this.bUsMatericUnits = bUsMatericUnits;
    }
    public boolean getUsMatercUnits(){
        return this.bUsMatericUnits;
    }

    public void setUsMatericUnits(boolean bUseatericUnits){
        this.bUsMatericUnits = bUseatericUnits;
    }

    @Override
    public float distanceTo(Location dest) {

        float nDistance = super.distanceTo(dest);

        if (!this.getUsMatercUnits()){
            nDistance = nDistance * 3.28083989501312f;
        }
        return nDistance;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();

        if (!this.getUsMatercUnits()){
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed()* 3.6f;

        if (!this.getUsMatercUnits()){
            nSpeed = super.getSpeed() * 2.26393629f;
        }
        return nSpeed;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();

        if (!this.getUsMatercUnits()){
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
    }




}
