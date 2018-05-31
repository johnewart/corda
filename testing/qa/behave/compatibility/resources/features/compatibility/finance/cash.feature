@compatibility @node @cordapps
Feature: Compatibility - Mixed Corda distributions (OS and Enterprise) running different CorDapps
  To support an interoperable Corda network, different CorDapps must have the ability to transact in mixed Corda (OS) and Corda Enterprise networks.

  Scenario Outline: Corda (OS) Node can transact with Corda Enterprise node using Finance Cash application.
    Given a node partyA of version <Corda-Node-Version> with proxy
    And node partyA has the finance app installed
    And a node partyB of version <R3-Corda-Node-Version> with proxy
    And node partyB has the finance app installed
    And a nonvalidating notary Notary of version <Corda-Node-Version>
    When the network is ready
    Then node partyA can issue 1000 <Currency>
    And node partyA can transfer 100 <Currency> to node partyB

    Examples:
      | Corda-Node-Version    | R3-Corda-Node-Version   | Currency |
      | corda-master          | r3-master               | GBP      |
      | corda-master          | r3-master               | USD      |
      | corda-master          | r3-master               | CHF      |
      | corda-master          | r3-master               | EUR      |

