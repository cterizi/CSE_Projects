using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class destroyCube : MonoBehaviour {

	public GameObject remains;

	int livesLeft;

	// Use this for initialization
	void Start () {
		livesLeft = 3;
	}

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.name == "Hammer") {
			livesLeft -= 1;
			GameObject.Find ("FPSController").GetComponent<throwHammer> ().immediateDestruct ();
			if (livesLeft <= 0) {
				Instantiate(remains, transform.position, transform.rotation);
				Destroy (gameObject);
			}
		}
		if (col.gameObject.tag == "fps") {
			print ("COLL");
		}
	
	}

	public void changeAlpha(bool b){
		MeshRenderer r = GetComponent<MeshRenderer> ();
		Color color = r.material.color;
		if (b)
			color.a = 0.4f;
		else
			color.a = 1f;
		r.material.color = color;
	}

	// Update is called once per frame
	void Update () {
		

	}
}
