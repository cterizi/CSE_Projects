using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class throwHammer : MonoBehaviour {

	public GameObject hammer;
	AudioClip hammer_sound;  
	AudioSource audioSource;
	public int HP = 100;
	int index;
	byte[,] colorMap = new byte[,] {{246, 18, 18}, {233, 25, 25}, {222, 27, 27}, {206, 37, 37}, {186, 43, 43}, {154, 39, 39}, {139, 35, 35}, {122, 30, 30}, {104, 25, 25}, {71, 17, 17}};
	bool throwing = false, firstTime = true;
	Rigidbody rb;

	// Use this for initialization
	void Start () {
		hammer.SetActive (true);
		audioSource = gameObject.AddComponent <AudioSource>();
		hammer_sound = (AudioClip)Resources.Load("hammer_hit");
		index = HP/10 - 1;
		foreach (MeshRenderer c in hammer.GetComponentsInChildren<MeshRenderer>()) {
			c.material.color = new Color32(colorMap[9-index, 0], colorMap[9-index, 1], colorMap[9-index, 2], 255); //Color.red;
		}
		hammer.transform.localPosition = new Vector3 (0f, -0.4f, 0.5f);
		hammer.transform.localRotation = transform.rotation;
		hammer.transform.forward = transform.forward;
			
		throwing = false;
	}

	public void resetHammer(){
		if (!firstTime) {
			HP = 100;
			index = HP/10 - 1;
			foreach (MeshRenderer c in hammer.GetComponentsInChildren<MeshRenderer>()) {
				c.material.color = new Color32(colorMap[9-index, 0], colorMap[9-index, 1], colorMap[9-index, 2], 255); //Color.red;
			}
			hammer.SetActive (true);
			firstTime = true;
			throwing = false;
		}
	}
	
	// Update is called once per frame
	void Update () {

		if (HP == 0 && firstTime) {
			GameObject.Find ("Map Init").GetComponent<createMaze> ().removeHammer ();
			firstTime = false;
		}

		if (Input.GetMouseButtonDown (0) && !throwing) {
			index = HP / 10 - 1;
			HP -= 10;
			foreach (MeshRenderer c in hammer.GetComponentsInChildren<MeshRenderer>()) {
				c.material.color = new Color32(colorMap[9-index, 0], colorMap[9-index, 1], colorMap[9-index, 2], 255); //Color.red;
			}
			StartCoroutine (destruct ());
			hammer.transform.localPosition = new Vector3 (0f, -0.4f, 0.5f);
			hammer.transform.rotation = transform.rotation;
			rb = hammer.AddComponent<Rigidbody>();
			rb.AddForce(Camera.main.transform.forward * 300);
			throwing = true;
			audioSource.PlayOneShot (hammer_sound);
		}

		if (Input.GetKeyDown (KeyCode.K)) { 
			HP = 100;
		}
	}

	IEnumerator destruct(){
		yield  return new WaitForSeconds(1.5f);
		Destroy (hammer.GetComponent<Rigidbody>());
		hammer.transform.localPosition = new Vector3 (0f, -0.4f, 0.5f);
		hammer.transform.rotation = transform.rotation;
		throwing = false;
		if (HP == 0) {
			if (GameObject.Find ("Map Init").GetComponent<createMaze> ().K == 0) {
				hammer.SetActive (false);
				throwing = true;
			} else {
				HP = 100;
				index = HP/10 - 1;
				foreach (MeshRenderer c in hammer.GetComponentsInChildren<MeshRenderer>()) {
					c.material.color = new Color32(colorMap[9-index, 0], colorMap[9-index, 1], colorMap[9-index, 2], 255);
				}
				firstTime = true;
			}
		}
	}

	public void immediateDestruct(){
		StopCoroutine (destruct ());
		Destroy (hammer.GetComponent<Rigidbody>());
		hammer.transform.localPosition = new Vector3 (0f, -0.3f, 0.5f);
		hammer.transform.rotation = transform.rotation;
		throwing = false;
		if (HP == 0) {
			if (GameObject.Find ("Map Init").GetComponent<createMaze> ().K == 0) {
				hammer.SetActive (false);
				throwing = true;
			} else {
				HP = 100;
				index = HP/10 - 1;
				foreach (MeshRenderer c in hammer.GetComponentsInChildren<MeshRenderer>()) {
					c.material.color = new Color32(colorMap[9-index, 0], colorMap[9-index, 1], colorMap[9-index, 2], 255); 
				}
				firstTime = true;
			}
		}
			
	}
}
