# Cloud Simulators
## Team members: 
<table>
    <thead>
        <tr>
            <td><b>Name</b></td>
            <td><b>UIN</b></td>
            <td><b>Email</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Rishabh Goel</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Samihan Nandedkar</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Amey Kasbe</td>
            <td>674285381</td>
            <td>akasbe2@uic.edu</td>
        </tr>
    </tbody>
<table>



## Project Description
The intention of the project is to simulate cloud computing infrastructure, execute and evaluate different cloud models with different VM allocation polices, VM scheduling policies and Cloudlet scheduling policies. The project is created using [CloudSim Plus](https://cloudsimplus.org/) framework. The framework helps mode, simulate and experiment on cloud environments with its rich libraries. This allows the developers to focus on the system design issues, without worrying about the low-level details of the cloud infrastructure.

## Simulation Environment Architecture
![](etc/simulationEnvArchi.png)

### Entities
#### CIS
* CIS stands for Cloud Information Service
* It is a registry that maintains the entries of the resource entities available on the cloud like datacenters, hosts etc.

#### Cloudlets
* Workloads or jobs or tasks that are executed during the simulation of the cloud infrastructure.

#### Broker
* Acts on the behalf of the cloud customer.
* Submits VM requests and cloudlets to Datacenter
* Communicates with CIS to get resource information

## Project Files
Some important project files -

### Simulation.scala
The main method inside the object `Simulation` executes all simulations sequentially.

### Utilities
#### DatacenterUtils.scala
* Class `DatacenterUtils` creates datacenters, hosts, virtual machines and cloudlets.
* It's structure is as -
  ![](etc/DatacenterUtils.png)

* Methods `createDatacenter()`, `createHost()`, `createVms()`, `createCloudlets()` create Datacenter(s), Host(s), Virtual Machine(s) and Cloudlet(s) respectively.

#### GetDatacenterConfig.scala
* The class GetDatacenterConfig parses a config file and gets the configuration of datacenter(s) and cost of running a datacenter.

![](etc/GetDatacenterConfig.png)

#### GetHostConfig.scala
* The class GetHostConfig parses a config file and gets the configuration of host(s).

![](etc/GetHostConfig.png)

#### GetVmConfig.scala
* The class GetVmConfig parses a config file and gets the configuration of virtual machine(s).

![](etc/GetVmConfig.png)

#### GetCloudConfig.scala
* The class GetCloudConfig parses a config file and gets the configuration of cloudlet(s).

![](etc/GetCloudletConfig.png)

### SchedulingSimulation.scala
* A class to simulate Time shared and Space shared VM and Cloudlet scheduling policies.
* On the basis of a string `schedulerModel`, TimeShared.conf or SpaceShared.conf file is parsed to get the infrastructural configurations.
* Arguments vmScheduler that is a `VmScheduler` instance and cloudletScheduler, a `CloudletScheduler` instance, scheduling policies are decided.
* The VM Allocation Policy is taken as the default VmAllocationPolicySimple.

### VmAllocatPolicyRoundRobin.scala
* The class `VmAllocatPolicyRoundRobin` shows the simulation of the VM Allocation Policy Round Robin in which VMs are assigned to the hosts in a cyclic manner.

### CloudModelsSimulation.scala
* The class `CloudModelsSimulation` is to simulate three datacenters, each for three different cloud models - Saas, Paas and Iaas arranged in BRITE topology.

## Simulations
### 1. Space Shared VM and Cloudlet Scheduling Policies Simulation
* A datacenter with 1 host, 1 VM are created and 4 cloudlets are executed in this system.
* The VM Allocation policy is VmAllocationPolicySimple.
* The VM Scheduling policy is VmSchedulerSpaceShared and the cloudlet scheduling policy is CloudletSchedulerSpaceShared.
* As a result, the cloudlets are executed sequentially.
  ![](etc/SpaceShared.png)
* The first cloudlet completes its execution in 40 seconds. Till then the next cloudlet waits for the resources to be available.
* All the PE resources are utilized one by one by all the cloudlets.

### 2. Time Shared VM and Cloudlet Scheduling Policies Simulation
* A datacenter with 1 host, 1 VM are created and 4 cloudlets are executed in this system.
* The VM Allocation policy is VmAllocationPolicySimple.
* The VM Scheduling policy is VmSchedulerTimeShared and the cloudlet scheduling policy is CloudletSchedulerTimeShared.
* As a result, the cloudlets are executed in such a manner that they are take a fraction time of the host resources.
  ![](etc/TimeShared.png)
* All the cloudlets take similar 160 seconds to finish.

#### Inference
* The total time to execute four identical cloudlets are similar i.e. 160 seconds. In Time Shared policy, although, all cloudlets start their execution at nearly same time, it does not mean the execution is finished in the time of one cloudlet.
* Cost of both the executions are similar as shown in the simulation.

#### Space Shared Policy Cost
![](etc/SpaceSharedCost.png)

#### Time Shared Policy Cost
* ![](etc/TimeSharedCost.png)

### 3. Round Robin VM Allocation Policy simulation
* A datacenter with 4 hosts, 8 VMs are created and 8 cloudlets are executed in this system.
* The VM Allocation policy is VmAllocationPolicyRoundRobin.
* The VM Scheduling policy is VmSchedulerSpaceShared and the cloudlet scheduling policy is CloudletSchedulerTimeShared.
* As a result, VMs are allocated to host in a round robin (cyclic) manner. Once one VM is allocated to a host, next one is allocated to the next host and so on. When every host is occupied, the next VM is allocated to the first Host and so on.
* It is a naive approach and it increases the number of active hosts leading to resource wastage.


![](etc/RoundRobin1.png)
![](etc/RoundRobin2.png)

* On looking at the log, it can be inferred that Virtual Machines are allocated to Hosts cyclically. VM0 to VM7 are allocated to Host0 to Host3 cyclically.

### 4. Cloud models simulation (Saas, Paas and Iaas)
* Three datacenters are created each for the three cloud models - Saas, Paas and Iaas.
* The datacenters and the broker are connected with each other in BRITE topology.
* The configurations for the datacenters are -
    * Saas - 2 hosts, 1 VM, 2 cloudlets
    * Paas - 2 hosts, 1 VM, 2 cloudlets
    * Iaas - 2 hosts, 1 VM, 2 cloudlets
    * Cost for each datacenter is different.
      Complete configurations can be found in `Saas.conf`, `Paas.conf` and `Iaas.conf`.
* Each of the configuration files are divided into two parts on the basis of who can decide which part of the configuration.
    * Configuration handled by the cloud service provider
    * Configuration handled by the cloud consumer.

#### Software as a Service
* In Software as a Service model the consumer only has a user level and limited admin level control over the application.
* So, the only configuration a consumer controls in Saas model is number of cloudlets. Rest is controlled by cloud service provider.

#### Platform as a Service
* In Platform as a Service model, the consumer is given more control over the infrastructure. The consumer has complete control over the application and has development tools and environment to build applications.
* So, the consumer can control the configuration of the cloudlets and the number of VMs to be requested.
* Faas, being a special case of Paas is not implemented.

#### Infrastructure as a Service
* In Infrastructure as a Service model, the consumer can request configuration of the virtual machines as well. The consumer has control over almost all parts of the infrastructure apart from the most priviledged lower levels.
* So, the consumer can control the configuration of the cloudlets, virtual machines and the number of hosts.

#### Simulation
![](etc/CloudModels1.png)

#### Cost of execution
![](etc/CloudModels2.png)

* Taking cost under consideration, Iaas is the most expensive cloud model (Datacenter 1) and Saas to be the least expensive option.
* This is due to the fact that it offers more configuration ability to the consumer. It still has Paas and Saas layers on top of it to be functional, making the cost of each of its resource to be higher.
* Similarly, Paas is the next expensive option which makes Saas the least expensive.

## Execution procedure
### Executing the runSimulation method
1. Clone this repository
2. Import the project in IntelliJ
3. Execute the `runSimulation` main method from the Simulation class.

### By SBT run command
1. Clone this repository
2. Ensure necessary dependencies are installed
    * Java development kit
    * Scala runtime
    * SBT
3. In terminal, go to path `src/main/scala/` directory
4. Execute - `$ sbt clean compile run`

## Unit testing procedure
### Using the CloudSimulatorTestSuite class
1. Clone this repository
2. Import the project in IntelliJ
3. Run the `CloudSimulatorTestSuite` class from `CloudSimulatorTestSuite.scala`.

### By SBT test command
1. Clone this repository
2. Ensure necessary dependencies are installed
    * Java development kit
    * Scala runtime
    * SBT
3. In terminal, go to path `src/main/scala/ `directory
4. Execute - `$ sbt clean compile test`

## References
1. Dr. Grechanik, Mark, (2020) Cloud Computing: Theory and Practice.
2. [Rock the JVM](https://www.youtube.com/c/RocktheJVM)
3. [SuperWits Academy](https://www.youtube.com/channel/UCc0MW9_-0n7wHsX73DnfkKQ)
4. [Cloudsim Tutorials](https://www.cloudsimtutorials.online/)
