package com.hstat.tgb.dto;


/**
 * DTO to send a result of the dialog to stat service
 * @param med
 * @param feel
 */
public record StatSend(String med, int feel) {
}
