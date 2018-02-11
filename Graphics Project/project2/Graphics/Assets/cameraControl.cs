using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class cameraControl : MonoBehaviour {

	public GameObject mainCamera;
	public GameObject fpsCamera;
	public GameObject cylinder;

	Transform target;
	int N_, L_;
	bool isAtTop = false;

	bool rotating = false;
	bool firstTimeClicked = true;
	// Use this for initialization
	Transform prevTransform;

	void Start () {

	}
	void OnGUI(){
		if (isAtTop) {
			GUI.Box (new Rect (Screen.width/2-100, Screen.height/2-50, 240, 50), "");
			GUI.Label (new Rect (Screen.width/2 - 45, Screen.height/2-40, 200, 20), "Press E to end the game");
		}
	}
	
	// Update is called once per frame
	void Update () {

		if (Input.GetKeyDown (KeyCode.V)) { 

			if (!mainCamera.activeSelf) {
				cylinder.SetActive(true);
				cylinder.transform.position = fpsCamera.transform.position;
			}else{
				cylinder.SetActive(false);
			}

			mainCamera.SetActive (!mainCamera.activeSelf);
			fpsCamera.SetActive (!fpsCamera.activeSelf);
	
			destroyCube[] dc = (destroyCube[])GameObject.FindObjectsOfType (typeof(destroyCube));

			foreach (destroyCube element in dc){
				element.changeAlpha (mainCamera.activeSelf);
			}
		}

		else if (Input.GetKeyDown (KeyCode.R) && mainCamera.activeSelf) { 
			rotating = !rotating;
		}
		else if (Input.GetKeyDown (KeyCode.E) && isAtTop){
			GameObject.Find ("Map Init").GetComponent <createMaze>().endGame();
		}

		if (rotating && mainCamera.activeSelf) {
			if (firstTimeClicked) {
				firstTimeClicked = false;
				GameObject.Find ("Map Init").GetComponent<createMaze> ().setCameraPosition(mainCamera);
				N_ = GameObject.Find ("Map Init").GetComponent <createMaze>().N;
			}
			mainCamera.transform.RotateAround(new Vector3(N_/2,0f,N_/2), Vector3.up, 10*Time.deltaTime); 
		}

		if (fpsCamera.activeSelf) {
			L_ = GameObject.Find ("Map Init").GetComponent <createMaze>().L;
			if (fpsCamera.transform.position.y >= L_ - 0.2f) {
				isAtTop = true;

			} else {
				isAtTop = false;
			}
		}
	}

	public void enableMainCamera(){
		mainCamera.SetActive (true);
		fpsCamera.SetActive(false);
	}
		
}
