﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Jump : MonoBehaviour {
	private Rigidbody2D body;
    public AudioSource playerJump;
    public AudioSource powerGrab;
    public AudioSource powerDown;
    public AudioSource lose;
    public TextMesh text;
	public float jump;
	public float right;
    public Sprite jumpSprite;
	private bool canJump = true;
    public static float distanceTraveled;
    public int points = 0;

    

    // Use this for initialization
    void Start () {
        body = gameObject.GetComponent<Rigidbody2D> ();
    

    }		

	
	// Update is called once per frame
	void FixedUpdate () {
        
        transform.Translate(5f * Time.deltaTime, 0f, 0f);
        distanceTraveled = transform.localPosition.x;
        // once touched a platform allow player to jump again
        if (body.velocity.y == 0) {
			canJump = true;
		}

		if (Input.GetKeyDown (KeyCode.UpArrow) && this.canJump == true) {
			Vector2 vec = new Vector2 (12f, jump);
			body.AddForce (vec * 10);
            playerJump.Play();
            canJump = false;
		}
		if (Input.GetKeyDown (KeyCode.RightArrow)) {
			Vector2 rVec = new Vector2 (right, 0f);
			body.AddForce (rVec * 10);
		}
        if (Input.GetKeyDown(KeyCode.LeftArrow))
        {
            Vector2 rVec = new Vector2(-right, 0f);
            body.AddForce(rVec * 10);
        }
        text.text = points.ToString();
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.tag == "Power")
        {
            points += 50;
            powerGrab.Play();
        }
        if (collision.gameObject.tag == "Enemy")
        {
            powerDown.Play();
            if (points > 50)
            {
                points -= 50;
            }
            else
                points = 0;
        }
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "Platform")
        {
            points++;  
        }
    }

}
