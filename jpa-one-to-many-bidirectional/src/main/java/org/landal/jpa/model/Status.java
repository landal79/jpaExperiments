package org.landal.jpa.model;

public enum Status {

	/** Il serial kit è in confezionamento e non ancora finito **/
	INCOMPLETE,
	/** Ha finito il confezionamento (in teli) oppure è stato creato **/
	NEW,
	/** Associato contenitore **/
	CONTAINER,
	/** Associato autoclave (diventerà ASSOCIATED) sia sterilizzazione che lavaggio **/	
	/** sterilizzato **/
	STERILIZED,
	/** Spedito al cliente **/
	SENT,
	/** è stato effettuato il check-in **/
	CONSUMED,
	/** E' stato riordinato (v.ordini rapidi) **/
	REORDERED,
	/** Kit invalidato **/
	INVALIDATED,	
	/** Accettato **/
	ACCEPTED,
	/** Associato apparato **/
	ASSOCIATED,
	/** Decontaminato **/
	DECONTAMINATED,
	/** Lavato **/
	WASHED,
	/** Confezionato **/
	PACKED,
	/** In conto lavorazione da un fornitore per la sterilizzazione **/
	STER_CONTRACT_WORK,
	/** Accettato pulito**/
	ACCEPTED_CLEANED;
	
}
