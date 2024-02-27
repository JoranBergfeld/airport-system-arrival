# airport-system-arrival
This is a simple airport system that simulates the arrival of airplanes to an airport. 


Todo:
- ~~Find a way to actually track these ideas in a decent system. Not some bloody readme file. :-)~~
- ~~Unlocking of gates. If an arrival holds a gate, this should be released after a certain time, or when the time of arrival has passed.~~
- Time handling seems to be a bit off on local machine. Investigate.
- Refactor code to make more sense and separate the split between arrival and schedule.
- Create a service which "mocks" the scheduling of arrivals automatically. 
- Rethink gate occupation. The scheduling domain is now responsible for the lifecycle of a gate. The releasing of a gate should be done by ground operations once an airplane has left the gate.
- Gate is a rather restrictive way of modelling a place where an airplane can be located. Potentially this should be a more generic concept, such as a location. This also allows airplanes to be parked. 
- Create ground operations service, which should be responsible for the lifecycle of an airplane. The ground operations should be triggered once an airplane has arrived at a gate, and take responsibility of parking, refueling, baggage handling, boarding and departure of the plane.
