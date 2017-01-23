# layercake

## what is this?

layercake is a distributed peer-to-peer file system that is designed with two major goals in mind:

1. censorship resistance
2. plausible deniability (for all participants)

in practice, this means: when Alice has uploaded a file X, then

1. it should be (nearly) impossible for Mallory to damage, suppress or remove it.
2. it should be (nearly) impossible for Eve to prove that Alice has uploaded file X, or that Bob has downloaded it, or that Charlie has shared (part of) it.

both goals go hand in hand, as the design of the system tries to defeat both technical and legal censorship attempts.

## how does it work?

layercake is a distributed file system. each file is split into multiple fixed-sized blocks, which are stored on differend nodes. blocks are content-adressed by a 128-bit cryptographic hash of their binary content. conceptually, all 2^128 block adresses form a ring, and each block is stored in the closest ring location to its adress.

nodes are divided into servers (long-lived, with permanent IP adress) and clients (short-lived, with NAT adress). clients are expected to outnumber servers by 100:1 or more. clients are run by end users, and responsible for uploading / downloading files, and caching popular blocks. servers are responsible for long-term storage of blocks, and bootstrapping of clients and servers.

the basic idea behind layercake is "block stacking". when uploading a new file, each block A is paired with a random existing block B, which are XOR'ed into C. the adresses of B and C are recorded, and C is stored in the ring. to later restore A, both B and C have to be downloaded.

while this doubles the download (but not the upload) size of each file, it means that **each block belongs to multiple files**, and each file consists of blocks from multiple files. the same block can be part of the "declaration of independence" and the "unabomber manifesto"! this constitutes the basic layer of plausible deniability in layercake, and also gives the system its name.


* 4-way redundancy, read repair 1
* parity blocks, read repair 2


## file encoding

* encrypt
* split into blocks
* proof-of-work: equihash
* block pairs
* parity blocks
* index blocks
* root block

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
* job: read repair
* client bootstrap
* api
	* FIND
	* LOAD
	* STORE

## faq