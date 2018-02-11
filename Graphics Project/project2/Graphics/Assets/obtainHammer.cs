using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class obtainHammer : MonoBehaviour {

	AudioClip obtainHammerSound; 
	AudioSource audioSource;

	// Use this for initialization
	void Start () {
		audioSource = gameObject.AddComponent <AudioSource>();
		obtainHammerSound = (AudioClip)Resources.Load("obtainHammerSound");
	}

	void OnTriggerEnter(Collider hit){

		if (hit.gameObject.tag == "fps") {
			GameObject.Find ("Map Init").GetComponent<createMaze> ().addHammer ();
			hit.gameObject.GetComponent<throwHammer> ().resetHammer ();
			audioSource.PlayOneShot (obtainHammerSound);
			gameObject.transform.position = new Vector3(1000, 1000, 1000);
		}
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
