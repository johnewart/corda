package com.r3.corda.networkmanage.common.persistence

import java.math.BigInteger
import java.security.cert.CRLReason
import java.time.Instant

data class CertificateRevocationRequestData(val requestId: String, // This is a uniquely generated string
                                            val certificateSerialNumber: BigInteger,
                                            val revocationTime: Instant?,
                                            val legalName: String,
                                            val status: RequestStatus,
                                            val reason: CRLReason,
                                            val reporter: String) // Username of the reporter

/**
 * Interface for managing certificate revocation requests persistence
 */
interface CertificateRevocationRequestStorage {

    /**
     * Creates a new revocation request for the given [certificateSerialNumber].
     * The newly created revocation request has the [RequestStatus.NEW] status.
     * If the revocation request with the [certificateSerialNumber] already exists and has status
     * [RequestStatus.NEW], [RequestStatus.APPROVED] or [RequestStatus.REVOKED]
     * then nothing is persisted and the existing revocation request identifier is returned.
     *
     * @param certificateSerialNumber serial number of the certificate to be revoked.
     * @param reason reason for revocation. See [java.security.cert.CRLReason]
     * @param reporter who is requesting this revocation
     *
     * @return identifier of the newly created (or existing) revocation request.
     */
    fun saveRevocationRequest(certificateSerialNumber: BigInteger, reason: CRLReason, reporter: String): String

    /**
     * Retrieves the revocation request with the given [requestId]
     *
     * @param requestId revocation request identifier
     *
     * @return CertificateRevocationRequest matching the specified identifier. Or null if it doesn't exist.
     */
    fun getRevocationRequest(requestId: String): CertificateRevocationRequestData?

    /**
     * Retrieves all the revocation requests with the specified revocation request status.
     *
     * @param revocationStatus revocation request status of the returned revocation requests.
     *
     * @return list of certificate revocation requests that match the revocation request status.
     */
    fun getRevocationRequests(revocationStatus: RequestStatus): List<CertificateRevocationRequestData>

    /**
     * Changes the revocation request status to [RequestStatus.APPROVED].
     *
     * @param requestId revocation request identifier
     * @param approvedBy who is approving it
     */
    fun approveRevocationRequest(requestId: String, approvedBy: String)

    /**
     * Changes the revocation request status to [RequestStatus.REJECTED].
     *
     * @param requestId revocation request identifier
     * @param rejectedBy who is rejecting it
     * @param reason description of the reason of this rejection.
     */
    fun rejectRevocationRequest(requestId: String, rejectedBy: String, reason: String)
}