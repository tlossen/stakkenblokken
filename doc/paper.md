# stakkenblokken

## what is this?

stakkenblokken is a distributed peer-to-peer file system that is designed with two major goals in mind:

1. censorship resistance
2. plausible deniability (for all peers)

in practice, this means:

1. when Alice has uploaded a file X, it should be (nearly) impossible for Mallory to remove, damage or suppress it.
2. it should be (nearly) impossible for Eve to prove that Alice has uploaded X, or that Bob has downloaded it.


## how does it work?

* distributed file system
* block storage with content adressing ("128 bit ring")
* 4-way redundancy, read repair
* "block stacking"
* peer-to-peer, servers and clients

## servers

* job: persistent storage
* job: client bootstrapping
* job: client routing
* server bootstrap
* api
	* HELLO
	* SYNC
	* FIND
	* LOAD
	* STORE

## clients

* job: transient storage / cache
* job: client routing
* job: end user interface
* client bootstrap
* api
	* FIND
	* LOAD
	* STORE

