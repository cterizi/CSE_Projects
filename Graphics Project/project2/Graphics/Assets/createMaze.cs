using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;

public class createMaze : MonoBehaviour {

	public int L, N, K;
	public int score;
	bool gameEnd = false;

	float rand = 0;

	void Start () {
		FileInfo theSourceFile = new FileInfo("C://Users//KOUZOU//Documents//Graphics//Assets//Resources//maze.txt");
		StreamReader reader = theSourceFile.OpenText();

		string []text;
		text = reader.ReadLine ().Split ('=');
		L = int.Parse (text [1].Replace (" ", ""));
		text = reader.ReadLine ().Split ('=');
		N = int.Parse (text [1].Replace (" ", ""));
		text = reader.ReadLine ().Split ('=');
		K = int.Parse (text [1].Replace (" ", ""));

		int counter = 0, emptyCounter = 0;
		int i,j,l = 0;
		GameObject firstTeleportCube = null;
		GameObject cube;
		bool isFirstTeleport = true;

		for (l = 0; l < L; l++) {
			isFirstTeleport = true;
			reader.ReadLine (); //Level x
			for (i = 0; i < N; i++) {

				text = reader.ReadLine().Split(' ');
				j = 0;

				foreach (var s in text) {

					s.Replace (" ", "");
					if (s.Equals("")) {
						continue;
					}
			
					if (!s.Equals ("E")) {
						if (s.Equals ("R")) {
							cube = (GameObject)Instantiate (Resources.Load ("RedCube"));
						} else if (s.Equals ("B")) {
							cube = (GameObject)Instantiate (Resources.Load ("BlueCube"));
						} else if (s.Equals ("G")) {
							cube = (GameObject)Instantiate (Resources.Load ("GreenCube"));
						} else if (s.Equals ("T1")) {
							cube = (GameObject)Instantiate (Resources.Load ("T1Cube"));
						} else if (s.Equals ("T2")) {
							cube = (GameObject)Instantiate (Resources.Load ("T2Cube"));
						} else if (s.Equals ("T3")) {
							cube = (GameObject)Instantiate (Resources.Load ("T3Cube"));
						} else { //is W = teleport
							cube = (GameObject)Instantiate (Resources.Load ("TeleportCube"));
							if (isFirstTeleport) {
								isFirstTeleport = false;
								firstTeleportCube = cube;
							} else {
								cube.GetComponent<teleport> ().secondCube = firstTeleportCube;
								firstTeleportCube.GetComponent<teleport> ().secondCube = cube;
							}
						} 

						cube.transform.position = new Vector3 (i, l, j);
						cube.GetComponent<BoxCollider> ().size = new Vector3 (0.99f, 0.99f, 0.99f);

						if (!s.Equals ("W")) {
							cube.GetComponent<destroyCube> ().changeAlpha (true);
						}



					} else {
						if (l == 0) {
							emptyCounter += 1;
							rand = Random.value;
							if (rand <= 1.0f / emptyCounter) {
								GameObject.Find ("CameraController").GetComponent<cameraControl> ().fpsCamera.transform.position = new Vector3 (i, l, j);
								GameObject.Find("Cylinder").transform.position = new Vector3 (i, l, j);
							}
						}
					}
					j += 1;

					counter += 1;
				}

			}

			for (i = -1; i <= N; i++) {
				cube = (GameObject)Instantiate (Resources.Load ("BoundBox"));
				cube.transform.position = new Vector3 (i, l, -1 - 0.5f);
				cube = (GameObject)Instantiate (Resources.Load ("BoundBox"));
				cube.transform.position = new Vector3 (i, l, N + 0.5f);
			}
			for (j = 0; j < N; j++) {
				cube = (GameObject)Instantiate (Resources.Load ("BoundBox"));
				cube.transform.position = new Vector3 (-1 - 0.5f, l, j);
				cube = (GameObject)Instantiate (Resources.Load ("BoundBox"));
				cube.transform.position = new Vector3 (N + 0.5f, l, j);
			}

		}
		GameObject.Find ("CameraController").GetComponent<cameraControl> ().fpsCamera.SetActive (false);
		GameObject.Find ("CameraController").GetComponent<cameraControl> ().mainCamera.SetActive (true);
		GameObject.Find("CameraController").GetComponent<cameraControl>().mainCamera.transform.position = new Vector3(N/2, 3*L+2, N/2);
		GameObject.Find ("CameraController").GetComponent<cameraControl> ().mainCamera.transform.Rotate (90, 90, 0);
		score = N * N;
		InvokeRepeating("decreaseScore", 0.0f, 1.0f);
	}

	public void addHammer(){
		K += 1;
	}

	public void removeHammer(){
		K -= 1;
		score -= 50;
		if (score <= 0)
			endGame ();
	}

	void decreaseScore(){//each second
		if (gameEnd) return;
		score -= 1;
		if (score <= 0)
			endGame ();
	}


	public void endGame(){
		gameEnd = true;
		GameObject.Find ("CameraController").GetComponent<cameraControl> ().enableMainCamera ();
		GameObject.Find ("CameraController").GetComponent<cameraControl> ().enabled = false;
	}

	public void setCameraPosition(GameObject cam){
		cam.transform.position = new Vector3(N/2, L/2 + 1,-N/2);
		cam.transform.rotation = Quaternion.Euler (0, 0, 0);
	}

	void OnGUI(){

		if (!gameEnd) {
			GUI.Box (new Rect (20, 10, 120, 40), "");
			GUI.Label (new Rect (25, 15, 100, 40), "Hammers Left: " + K);
			GUI.Box (new Rect (Screen.width - 120, 10, 120, 40), "");
			GUI.Label (new Rect (Screen.width - 100, 15, 70, 40), "Score: " + score);

		} else {
			GUI.Box (new Rect (Screen.width/2-100, Screen.height/2-50, 200, 100), "");
			GUI.Label (new Rect (Screen.width/2 - 45, Screen.height/2-40, 200, 20), "GAME OVER");
			GUI.Label (new Rect (Screen.width/2- 55, Screen.height/2-10, 200, 20), "Final Score: " + score);
		}
	}

	// Update is called once per frame
	void Update () {

		if (gameEnd)
			return;

		if (Input.GetKeyDown (KeyCode.X)) { 
			score = 0;
			endGame ();
		} 
	}
}