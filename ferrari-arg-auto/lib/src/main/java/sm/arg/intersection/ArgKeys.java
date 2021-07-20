/**
 *
 */
package sm.arg.intersection;

/**
 * @author sm
 *
 */
public enum ArgKeys {
    //If a car has been detected by RSU.
    CorrectlyDetected,
    //If a car hasn't been detected by RSU.
    WronglyDetected,
    //If RSU hasn't an efficient parameter alfa>standard value. 
    RSU_untrustworthy,
    //If RSU has an efficient parameter alfa>standard value. 
    RSU_trustworthy,
    //An event created by two different cars not detected and in conflict.
    PossibleIncident,
    //If two cars are not in conflict. 
    CanTransitSimultaneously,
    //If two cars are in conflict but are also detected, one car will reduce its speed and will obtain a waiting status.
    Wait,
    //If a possible incident became a real incident.
    Incident,
    //If two cars are in conflict but are also detected, one car will obtain a crossing status.
    PassesFirst,
    //If a car is Served by the system
    DeleteFromSystem,
    //If a car obtain the status Served
    Served

}
