package com.unimelb.swen30006.metromadness;

import java.util.ArrayList;

import javax.swing.text.FlowView.FlowStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Simulation {

	public ArrayList<Station> stations;
	public ArrayList<Line> lines;
	public ArrayList<Train> trains;
	private ObjectsRenderer mapRenderer;
	public ArrayList<Passenger> passengers;

	public Simulation(String fileName){
		// Create a map reader and read in the file
		MapReader m = new MapReader(fileName);
		m.process();

		// Create a list of lines
		this.lines = new ArrayList<Line>();
		this.lines.addAll(m.getLines());

		// Create a list of stations
		this.stations = new ArrayList<Station>();
		this.stations.addAll(m.getStations());
		
		//NEW get lines is new
		// For each station generate passengers 
		
		for (Station s : stations){
				PassengerGenerator passengerGenerator = new PassengerGenerator(s, s.getLines(),s.getMaxPassengers());
				Passenger[] passengers = passengerGenerator.generatePassengers();
				s.assignPassengers(passengers);
			} 
		//this.passengers = new ArrayList<Passenger>();

		// Create a list of trains
		this.trains = new ArrayList<Train>();
		this.trains.addAll(m.getTrains());

		mapRenderer = new ObjectsRenderer(this.lines, this.stations, this.trains);

	}

	// Update all the trains in the simulation
	public void update() {
		// Update all the trains
		for (Train t : this.trains) {
			t.update(Gdx.graphics.getDeltaTime());
		}
	}

	public void render(ShapeRenderer renderer) {
		mapRenderer.rendererObjects(renderer);

		// for(Line l: this.lines){
		// l.render(renderer);
		// }
		//
		// for(Train t: this.trains){
		// t.render(renderer);
		// }
		// for(Station s: this.stations){
		// s.render(renderer);
		// }
	}

}
