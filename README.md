# Simulator for argumentation-based coordination of autonomous connected vehicles at intersections

## Architecture

The picture below shows the main classes involved in a typical simulation, divided in three Java packages.

![Main classes involved in a typical simulation, and their relationships](/../master/2021-argumentation-driving-sw.png)

  * Package **"intersection"**: 
    Gathers most of the classes modelling the application domain, i.e. intersection crossing.
    A `JunctionsNetwork` is a square matrix of `Junction`s, each of which is composed by 4 `Road`s, 
    one for each `WAY` (north, east, south, west), 
    each of which is in turn including 3 lanes, 
    one for each turning `DIRECTION` (straight, left, right). 
    Finally, each `Road` may have a list of `RSU`s (Road Side Units) attached, 
    meant to provide perception and actuation capabilities 
    (e.g. sensing incoming vehicles, displaying messages to drivers, etc.). 
    The only mandatory RSU to deploy on `Road`s is the `DistanceRSU`, 
    meant to detect incoming vehicles at a configurable distance from the junction (e.g. 100 m).
  * Package **"argumentation"**: 
    Gathers all the classes involved in the argumentation process. 
    Each `Junction` is associated to a `JunctionArbiter` that during simulations 
    (i) is notified about `CrossingCars` approaching that `Junction` as detected by `DistanceRSU`s 
    (through method `addCar()`), and 
    (ii) is responsible for translating the state of the intersection into an argumentation theory to be used for cooperative driving 
    (method `argue()`), 
    i.e. assign right of ways, detect and resolve conflicts. 
    For doing so it relies on the ASPIC+ library provided by the [TweetyProject](https://tweetyproject.org).
    
    `CrossingCar`s are detected by RSUs on a certain `WAY` converging into the junction, 
    occupying a certain lane depending on next turning `DIRECTION`, 
    at a certain distance from the junction, 
    traveling with its speed, 
    and following a specific route amongst the many alternative routes potentially available to reach their destination. 
    The arbiter also assigns a `STATUS` to vehicles depending on both the state of the simulation and that of the argumentation process: 
    `APPROACHING` for vehicles just detected hence to be added to the argumentation theory, 
    `WAITING` for vehicles without the right of way, 
    `CROSSING` for vehicles with the right of way, and 
    `SERVED for vehicles that just crossed the current intersection.
    
    Finally, the `JunctionArbiter` exploits the `CrossingPolicy` attached to the associated `Junction` to resolve conflicting arguments in a configurable way.
    Ideed, by providing a specific implementation for the `rightOfWay()` method. 
    A few builtin policies are already provided; 
    in particular, the `AltRoutesPolicy` attempts to resolve conflicts by checking if conflicting vehicles have non-conflicting alternative routes available to reach their destination: 
    if so, both vehicles get the right of way simultaneously as one vehicle can change its current route to reach its destination anyway, 
    (hence the conflict is resolved); 
    if not, only one vehicle gets its right of way, 
    whereas the other has to wait 
    (hence the conflict is not resolved).
    
  * Package **"simulation"**: 
    Gathers all the classes specifically supporting running simulations. 
    In particular, a `NetworkSimulation` allows to simulate `CrossingCar`s travelling through a `JunctionsNetwork`, 
    whereas a `Simulation` allows to simulate a single `Junction`. 
    As such, a `NetworkSimulation` actually is a convenient aggregation of `Simulation`s: 
    an hash map associates a `Simulation` with a given `Junction` within the `JunctionNetwork`, 
    and the `NetworkSimulation` itself takes care of moving cars between the correct `Junction`s 
    (according to their provenance and direction) 
    by exploiting `JunctionMatrix` method `next()` and `CrossingCar` method `updateAfterCrossing()`.
    
  ## Workflow
  
Each `Simulation` follows the workflow summarised by the pseudocode below. 

<img src="/../master/alg2.png" width="400" height="500">

That is, it automatically spawns new vehicles in the corresponding `Junction` according to a `VehiclesGenerationStrategy` implemented by users 
(by defining method `newCars()`). 

A few builtin strategies are already provided; 
in particular, `NetworkRandomStrategy` creates 1 vehicle at a random `WAY` within the intersection, 
with random properties 
(e.g. speed, urgency, etc.) 
and with a random number of alternative routes available to reach a random destination requiring to cross a random number of junctions. 

These parameters can be controlled through appropriate configuration files. 
To ensure reproducibility of simulations, 
every random number generator exploited can be configured with a seed. 

Besides spawning vehicles, 
each simulation step 

  1. removes already served cars from the simulation, 
  2. populates the argumentation graph by translating the current junction and (remaining) cars situation into arguments, and 
  3. triggers the argumentation process to assign the right of way to remaining cars.

The workflow of a `NetworkSimulation` is instead shown in the pseudocode below. 

<img src="/../master/alg3.png" width="400" height="500">

Essentially, until either the maximum number of steps allowed for the simulation are reached, 
or all cars leave the simulation, 
each `Simulation` is triggered, 
and the list of cars leaving the related junction are relocated in the next junction of the network 
(if existing, depending on the car position and route in the network) 
and updated 
(e.g. its state, distance, and other parameters properly set).

The implemented simulator has some limitations: 
it can only simulate 4 ways junctions, 
and the intersection area is approximated as a point 
(hence, once a vehicles has obtained the right of way, crossing time is istantaneous).
