using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class selfDestruct : MonoBehaviour {

	public AudioClip explosion_sound;  
	public AudioSource audioSource;

	// Use this for initialization
	void Start () {

		audioSource = gameObject.AddComponent <AudioSource>();
		explosion_sound = (AudioClip)Resources.Load("explosion");
		audioSource.PlayOneShot (explosion_sound);

		StartCoroutine (destruct ());
		if (Random.value <= 0.5) {
			GameObject hammer = (GameObject)Instantiate (Resources.Load ("boxHammer"));
			hammer.transform.position = new Vector3(transform.position.x, transform.position.y - 0.3f, transform.position.z);

		}
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	IEnumerator destruct(){
		yield  return new WaitForSeconds(3);
		Destroy(gameObject);
	}


}

