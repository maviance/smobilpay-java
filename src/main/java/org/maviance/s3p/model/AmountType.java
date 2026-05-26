package org.maviance.s3p.model;

/**
 * How the payment amount is determined for a {@link PaymentItem}.
 *
 * <ul>
 *   <li>{@code FIXED} — must be paid in full at {@code amountLocalCur}.</li>
 *   <li>{@code CUSTOM} — caller chooses the amount.</li>
 *   <li>{@code PARTIAL} — amount may be less than {@code amountLocalCur}.</li>
 *   <li>{@code OVERPAY} — amount may exceed {@code amountLocalCur} (subject to country regulation).</li>
 * </ul>
 */
public enum AmountType {
    FIXED,
    CUSTOM,
    PARTIAL,
    OVERPAY
}
