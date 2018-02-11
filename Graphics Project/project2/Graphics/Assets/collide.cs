using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class collide : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}


	void OnCollisionEnter (Collision col)
	{
		if(col.gameObject.name == "RedCube")
		{
			Destroy (gameObject);
			//Destroy(col.gameObject);
			//livesLeft = 0;
		}
	}

	// Update is called once per frame
	void Update () {
		
	}
}
