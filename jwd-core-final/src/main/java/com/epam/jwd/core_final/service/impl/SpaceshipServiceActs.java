package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.AssignException;
import com.epam.jwd.core_final.exception.DuplicateException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.ReadinessException;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.apache.log4j.Logger;

import java.util.*;

public class SpaceshipServiceActs implements SpaceshipService {
    private static final Logger logger = Logger.getLogger(SpaceshipServiceActs.class);
    private static SpaceshipServiceActs instance;

    private SpaceshipServiceActs(){}

    public static SpaceshipServiceActs getInstance(){
        if(instance == null){
            instance= new SpaceshipServiceActs();
        }
        return instance;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) Application.nassaContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Spaceship> spaceships = (List<Spaceship>) Application.nassaContext.retrieveBaseEntityList(Spaceship.class);
        List<Spaceship> list = new ArrayList<>();
        if(((SpaceshipCriteria) criteria).getFlightDistance() != 0){
            spaceships.stream()
                    .filter(i -> i.getFlightDist() == ((SpaceshipCriteria) criteria).getFlightDistance())
                    .forEach(i -> list.add(i));
        }else{
            if(((SpaceshipCriteria) criteria).isReadyForNextMissions() != Boolean.parseBoolean(null)){
                spaceships.stream()
                        .filter(i -> i.isReadyForNextMissions() == ((SpaceshipCriteria) criteria).isReadyForNextMissions())
                        .forEach(i -> list.add(i));
            }
        }
        return list;
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Spaceship> spaceships = (List<Spaceship>) Application.nassaContext.retrieveBaseEntityList(Spaceship.class);
        Optional<Spaceship> spaceship = spaceships
                .stream()
                .filter(i -> i.getName().equals(((SpaceshipCriteria) criteria).getName()))
                .findAny();
        return spaceship;
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        if(spaceship.isReadyForNextMissions() != true){
            System.out.println("Spaceship busy now ! Try again next time...");
        }else {
            Scanner in = new Scanner(System.in);
            int buf;
            System.out.println("Enter which detail you want to update: \n" +
                    "1 - Flight distance\n" +
                    "2 - Requirement crew");
            do {
                while (!in.hasNextInt()) {
                    System.out.println("You need to enter number! Try again...");
                    in.nextLine();
                }
                buf = in.nextInt();
            } while (buf > 2 || buf < 1);
                switch (buf) {
                    case 1: {
                        System.out.println("Enter new flight distance: ");
                        in.nextLine();
                        while (!in.hasNextLong()) {
                            System.out.println("You need to enter number! Try again...");
                            in.nextLine();
                        }
                        long distance = in.nextLong();
                        spaceship.setFlightDist(distance);
                        System.out.println("Details of spaceship " + spaceship.getName() + " were updated.");
                        logger.info("Details of spaceship "+spaceship.getName()+" were updated.");
                        break;
                    }
                    case 2: {
                        System.out.println("Enter new requirement crew for this spaceship (number of crew members for this role): ");
                        Map<Role, Short> crew = new HashMap<>();
                        System.out.println(Role.MISSION_SPECIALIST);
                        in.nextLine();
                        while (!in.hasNextInt()) {
                            System.out.println("You need to enter number! Try again...");
                            in.nextLine();
                        }
                        buf = in.nextInt();
                        crew.put(Role.MISSION_SPECIALIST, (short) buf);
                        System.out.println(Role.FLIGHT_ENGINEER);
                        in.nextLine();
                        while (!in.hasNextInt()) {
                            System.out.println("You need to enter number! Try again...");
                            in.nextLine();
                        }
                        buf = in.nextInt();
                        crew.put(Role.FLIGHT_ENGINEER, (short) buf);
                        System.out.println(Role.PILOT);
                        in.nextLine();
                        while (!in.hasNextInt()) {
                            System.out.println("You need to enter number! Try again...");
                            in.nextLine();
                        }
                        buf = in.nextInt();
                        crew.put(Role.PILOT, (short) buf);
                        System.out.println(Role.COMMANDER);
                        in.nextLine();
                        while (!in.hasNextInt()) {
                            System.out.println("You need to enter number! Try again...");
                            in.nextLine();
                        }
                        buf = in.nextInt();
                        crew.put(Role.COMMANDER, (short) buf);
                        spaceship.setCrew(crew);
                        System.out.println("Details of spaceship" + spaceship.getName() + " were updated.");
                        logger.info("Details of spaceship "+spaceship.getName()+" were updated.");
                        break;
                    }
                    default:
                        System.out.println("Wrong number! try again...\n");
                }
        }
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws ReadinessException, InvalidStateException, AssignException {
        try {
            if (spaceship.isReadyForNextMissions() != true) throw new ReadinessException(spaceship.getName());
            else{
                MissionServiceActs missionServiceActs = MissionServiceActs.getInstance();
                Scanner str = new Scanner(System.in);
                FlightMissionCriteria flightMissionCriteria = new FlightMissionCriteria();
                System.out.println("Enter name of mission: ");
                String name = str.nextLine();
                flightMissionCriteria.setName(name);
                if(!missionServiceActs.findMissionByCriteria(flightMissionCriteria).isPresent()) throw new InvalidStateException("flight mission");
                else{
                    FlightMission flightMission = missionServiceActs.findMissionByCriteria(flightMissionCriteria).get();
                    if(flightMission.getAssignedSpaceShip() != null || flightMission.getMissionResult() != null) throw new AssignException("spaceship");
                    else{
                        if(flightMission.getDistance() > spaceship.getFlightDist()){
                            System.out.println("Spaceship " + spaceship.getName()+" does not available for this missions distance!");
                        }
                        else {
                            spaceship.setReadyForNextMissions(false);
                            flightMission.setAssignedSpaceShip(spaceship);
                            flightMission.setMissionResult(MissionResult.PLANNED);
                            logger.info("Spaceship "+spaceship.getName()+" was completely assigned on mission "+flightMission.getName()+".");
                            System.out.println("Spaceship "+spaceship.getName()+" was completely assigned on mission "+flightMission.getName()+".");
                        }
                    }
                }
            }
        }catch (ReadinessException | InvalidStateException | AssignException e ) {
            System.out.println(e.getMessage());
            logger.warn(e.getMessage() + " (assignSpaceshipOnMission)");
        }

    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws DuplicateException {
        List<Spaceship> spaceships = (List<Spaceship>) Application.nassaContext.retrieveBaseEntityList(Spaceship.class);
        try {
            Optional<Spaceship> spaceCheck;
            Spaceship finalSpaceship = spaceship;
            spaceCheck = spaceships.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(finalSpaceship.getName()))
                    .findAny();
            if (spaceCheck.isPresent()) {
                spaceship = spaceCheck.get();
                throw new DuplicateException(spaceship.getName(),"spaceship");
            }
            spaceships.add(spaceship);
            System.out.println("Spaceship "+spaceship.getName()+" was completely created.");
            logger.info("Spaceship "+spaceship.getName()+" was completely created.");
        }catch(DuplicateException e){
            System.out.println(e.getMessage());
            logger.warn(e.getMessage() + " (createSpaceship)");
        }
        return spaceship;
    }
}
