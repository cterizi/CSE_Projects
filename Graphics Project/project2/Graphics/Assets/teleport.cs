using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class teleport : MonoBehaviour {

	public GameObject secondCube;
	public bool enters = true;
	public bool exits = false;
	AudioClip teleportSound;  
	AudioSource audioSource;

	// Use this for initialization
	void Start () {
		audioSource = gameObject.AddComponent <AudioSource>();
		teleportSound = (AudioClip)Resources.Load("teleportSound");
	}

	void setSecondCube(GameObject cube){
		secondCube = cube;
	}

	void OnTriggerEnter(Collider hit){

		if (hit.gameObject.tag == "fps" && enters) {
			secondCube.GetComponent<ParticleSystem>().Play();
			secondCube.GetComponent<teleport> ().exits = true;
			secondCube.GetComponent<teleport> ().enters = false;
			audioSource.PlayOneShot (teleportSound);
			hit.gameObject.transform.position = secondCube.transform.position;
			
		}
	}

	void OnTriggerExit(Collider hit){
		if (hit.gameObject.tag == "fps" && exits) {
			secondCube.gameObject.GetComponent<teleport> ().exits = false;
			secondCube.gameObject.GetComponent<teleport> ().enters = true;
			enters = true;
			exits = false;
		}
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
