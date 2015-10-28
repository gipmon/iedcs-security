#ifndef __PTEIDLIB_H__
#define __PTEIDLIB_H__


#include "pteiddefines.h"

#ifdef __cplusplus
extern "C" {
#endif

#if defined(_WIN32) || defined (__WIN32__)
    #ifdef PTEIDLIB_EXPORTS
    #define PTEIDLIB_API __declspec(dllexport)
    #else
    #define PTEIDLIB_API __declspec(dllimport)
    #endif
#else
    #define PTEIDLIB_API
#endif

typedef struct 
{
    short version;
    char deliveryEntity[PTEID_MAX_DELIVERY_ENTITY_LEN];
    char country[PTEID_MAX_COUNTRY_LEN];
    char documentType[PTEID_MAX_DOCUMENT_TYPE_LEN];
    char cardNumber[PTEID_MAX_CARDNUMBER_LEN];
    char cardNumberPAN[PTEID_MAX_CARDNUMBER_PAN_LEN];
    char cardVersion[PTEID_MAX_CARDVERSION_LEN];
    char deliveryDate[PTEID_MAX_DATE_LEN];
    char locale[PTEID_MAX_LOCALE_LEN];
    char validityDate[PTEID_MAX_DATE_LEN];
    char name[PTEID_MAX_NAME_LEN];
    char firstname[PTEID_MAX_NAME_LEN];
    char sex[PTEID_MAX_SEX_LEN];
    char nationality[PTEID_MAX_NATIONALITY_LEN];
    char birthDate[PTEID_MAX_DATE_LEN];
    char height[PTEID_MAX_HEIGHT_LEN];
    char numBI[PTEID_MAX_NUMBI_LEN];
    char nameFather[PTEID_MAX_NAME_LEN];
    char firstnameFather[PTEID_MAX_NAME_LEN];
    char nameMother[PTEID_MAX_NAME_LEN];
    char firstnameMother[PTEID_MAX_NAME_LEN];
    char numNIF[PTEID_MAX_NUMNIF_LEN];
    char numSS[PTEID_MAX_NUMSS_LEN];
    char numSNS[PTEID_MAX_NUMSNS_LEN];
    char notes[PTEID_MAX_INDICATIONEV_LEN];
    char mrz1[PTEID_MAX_MRZ_LEN];
    char mrz2[PTEID_MAX_MRZ_LEN];
    char mrz3[PTEID_MAX_MRZ_LEN];
} PTEID_ID;

typedef struct 
{
    short version;
    char addrType[PTEID_MAX_ADDR_TYPE_LEN];
    char country[PTEID_MAX_ADDR_COUNTRY_LEN];
    char district[PTEID_MAX_DISTRICT_LEN];
    char districtDesc[PTEID_MAX_DISTRICT_DESC_LEN];
    char municipality[PTEID_MAX_DISTRICT_CON_LEN];
    char municipalityDesc[PTEID_MAX_DISTRICT_CON_DESC_LEN];
    char freguesia[PTEID_MAX_DISTRICT_FREG_LEN];
    char freguesiaDesc[PTEID_MAX_DISTRICT_FREG_DESC_LEN];
    char streettypeAbbr[PTEID_MAX_ROAD_ABBR_LEN];
    char streettype[PTEID_MAX_ROAD_LEN];
    char street[PTEID_MAX_ROAD_DESIG_LEN];
    char buildingAbbr[PTEID_MAX_HOUSE_ABBR_LEN];
    char building[PTEID_MAX_HOUSE_LEN];
    char door[PTEID_MAX_NUMDOOR_LEN];
    char floor[PTEID_MAX_FLOOR_LEN];
    char side[PTEID_MAX_SIDE_LEN];
    char place[PTEID_MAX_PLACE_LEN];
    char locality[PTEID_MAX_LOCALITY_LEN];
    char cp4[PTEID_MAX_CP4_LEN];
    char cp3[PTEID_MAX_CP3_LEN];
    char postal[PTEID_MAX_POSTAL_LEN];
    char numMor[PTEID_MAX_NUMMOR_LEN];
    char countryDescF[PTEID_MAX_ADDR_COUNTRYF_DESC_LEN];
    char addressF[PTEID_MAX_ADDRF_LEN];
    char cityF[PTEID_MAX_CITYF_LEN];
    char regioF[PTEID_MAX_REGIOF_LEN];
    char localityF[PTEID_MAX_LOCALITYF_LEN];
    char postalF[PTEID_MAX_POSTALF_LEN];
    char numMorF[PTEID_MAX_NUMMORF_LEN];
} PTEID_ADDR;

typedef struct 
{
    short version;
    unsigned char cbeff[PTEID_MAX_CBEFF_LEN];
    unsigned char facialrechdr[PTEID_MAX_FACRECH_LEN];
    unsigned char facialinfo[PTEID_MAX_FACINFO_LEN];
    unsigned char imageinfo[PTEID_MAX_IMAGEINFO_LEN];
    unsigned char picture[PTEID_MAX_PICTUREH_LEN];
    unsigned long piclength;
} PTEID_PIC;

typedef struct 
{
	unsigned char certif[PTEID_MAX_CERT_LEN];	/* Byte stream encoded certificate */
	long certifLength;					  /* Size in bytes of the encoded certificate */
	char certifLabel[PTEID_MAX_CERT_LABEL_LEN];     /* Label of the certificate (Authentication, Signature, CA, Root,) */
} PTEID_Certif;

typedef struct 
{
	PTEID_Certif certificates[PTEID_MAX_CERT_NUMBER];  /* Array of PTEID_Certif structures */
	long certificatesLength;			/* Number of elements in Array */
} PTEID_Certifs;

typedef struct 
{
  long pinType;             // ILEID_PIN_TYPE_PKCS15 or PTEID_PIN_TYPE_OS
  unsigned char id;                    // PIN reference or ID
  long usageCode;       // Usage code (PTEID_USAGE_AUTH, PTEID_USAGE_SIGN, ...)
  long triesLeft;
  long flags;
  char label[PTEID_MAX_PIN_LABEL_LEN];
  char *shortUsage;     // May be NULL for usage known by the middleware
  char *longUsage;      // May be NULL for usage known by the middleware
} PTEID_Pin;

typedef struct 
{
	PTEID_Pin pins[PTEID_MAX_PINS];  /* Array of PTEID_Pin structures */
	long pinsLength;			        /* Number of elements in Array */
} PTEID_Pins;

typedef struct 
{
	char label[PTEID_MAX_CERT_LABEL_LEN];
	char serial[PTEID_MAX_ID_NUMBER_LEN];
} PTEID_TokenInfo;

typedef struct 
{
	unsigned char bytes[PTEID_MAX_PERSO_FILE_LEN];
	unsigned long length;
} PTEID_PersoData;

/* The modulus and exponent are big integers in big endian notiation
 * (= network byte order). The first byte can be 0x00 allthough this is
 * not necessary (the value is allways considered to be positive).
 */
typedef struct
{
    unsigned char *modulus;
    unsigned long modulusLength;  // number of bytes in modulus/length of the modulus
    unsigned char *exponent;
    unsigned char exponentLength; // number of bytes in exponent/length of the exponent
} PTEID_RSAPublicKey;

/* Used in ChangeAddress() and ChangeCAPPIN*/
typedef struct {
	const char *csProxy;
	unsigned int uiPort;
	const char *csUserName;
	const char *csPassword;
} tProxyInfo;

/* Used in PTEID_GetCardType() */
typedef enum {
	CARD_TYPE_ERR = 0, // Something went wrong, or unknown card type
	CARD_TYPE_IAS07,   // IAS 0.7 card
	CARD_TYPE_IAS101,  // IAS 1.01 card
} tCardType;

/* Used in PTEID_GetChangeAddressProgress */
typedef enum {
	ADDR_INITIALISING = 0,    // Initialising
	ADDR_CONNECTING,          // Connecting to the Server (PIN is asked)
	ADDR_READING_INFO,        // Reading info (DH params, ...) from the card
	ADDR_SENDING_INFO,        // Sending info to the server
	ADDR_INIT_SEC_CHANNEL,    // Initialising the secure channel (DH)
	ADDR_SERVER_CHALL,        // Send challenge to the server
	ADDR_SERVER_AUTH,         // Let the card authenticate the server
	ADDR_CLIENT_AUTH,         // Let the server authenticate the card
	ADDR_SERVER_AUTH2,        // Second authentication of the server (Role Auth.)
	ADDR_WRITE,               // Write the new Address and SOD to the card
	ADDR_FINISH,              // Finish the address update (send responses to the server)
	ADDR_FINISHED,            // The Change Address flow has completed.
	ADDR_CANCELLED		      // The Change Address flow was cancelled.
} tAddressChangeState;

/* Used in PTEID_GetCapPinChangeProgress */
typedef enum {
	CAP_INITIALISING = 0,	// Initialising
	CAP_CONNECTING,			// Connecting to the Server (PIN is asked)
	CAP_READING_INFO,		// Reading info from the card
	CAP_SENDING_INFO,		// Sending info to the server
	CAP_WRITE,				// Change the PIN in the card
	CAP_FINISH,				// Finish the CAP PIN change(send responses to the server)
	CAP_FINISHED,			// The CAP PIN Change flow has completed.
	CAP_CANCELLED			// The CAP PIN Change flow was cancelled.
} tCapPinChangeState;

/* Used in Change Address & Change CAP PIN to report/check error codes inside HTTP messages. */
typedef enum {
	WEB_ERR_OK           =   0, // OK: The previous operation was successful.
	WEB_ERR_SELECT_FILE  =  51, // Select file error: There was an error finding a file in the card. Sw1 and sw2 are the response parameters.
	WEB_ERR_READ_FILE    =  52, // Read Error: There was an error reading a file from the card. Sw1 and sw2 are the response parameters.
	WEB_ERR_WRITE_FILE   =  53, // Write Error: There was an error writing on a file in the card. Sw1 and sw2 are the response parameters.
	WEB_ERR_BAD_COMMAND  =  54, // The card did not understand a generic command or refuses to serve it. Sw1 and sw2 are the response parameters.
	WEB_ERR_EMPTY_RES    =  55, // Empty response: The data returned from the card is of zero size.
	WEB_ERR_DATA_SIZE    =  56, // Data size not correct: The data returned from the card is not the correct size.
	WEB_ERR_CARD_REMOVED =  57, // Card removed: The user removed the card during operation.
	WEB_ERR_CARD_COMM    =  58, // Card communication problem: The middleware can't communicate with the card.
	WEB_ERR_OUT_OF_MEM   =  59, // Out of memory error.
	WEB_ERR_INTERNAL     =  60, // Internal middleware error.
	WEB_ERR_PARSING      =  61, // Error parsing server response: The content of the server response is invalid at the HTTP or JSON level.
	WEB_ERR_MISSING_DATA =  62, // Missing data in server response: The response from server lacks mandatory data.
	WEB_ERR_INVALID_HASH = 105,  // The card or the server has returned an invalid hash.
	WEB_ERR_INVALID_NONCE = 124  // The server has returned an invalid nonce, or nonce is missing.
} tWebErrorCode;

/**
 * Initializes the toolkit.
 * This function must be called before any other one;
 * it tries to connect to the card and in case no card is inserted an error is returned.
 * When the card is removed from the reader, this function must be called again.
 */
PTEIDLIB_API long PTEID_Init(
	char *ReaderName		/**< in: the PCSC reader name (as returned by SCardListReaders()),
									 specify NULL if you want to select the first reader */
);

#define PTEID_EXIT_LEAVE_CARD   0
#define PTEID_EXIT_UNPOWER      2

/**
 * Cleans up all data used by the toolkit, this function
 * must be called at the end of the program.
 */
PTEIDLIB_API long PTEID_Exit(
	unsigned long ulMode	/**< in: exit mode, either PTEID_EXIT_LEAVE_CARD or PTEID_EXIT_UNPOWER */
);

/**
 * Return true if the connected reader is a pinpad.
 */
PTEIDLIB_API int PTEID_IsPinpad();

/**
 * Return true if the connected reader is an EMV-CAP compliant pinpad.
 */
PTEIDLIB_API int PTEID_IsEMVCAP();

/**
 * Return the card type (CARD_TYPE_ERR, CARD_TYPE_IAS07 or CARD_TYPE_IAS101)
 */
PTEIDLIB_API tCardType PTEID_GetCardType();

/**
 * Read the ID data.
 */
PTEIDLIB_API long PTEID_GetID(
	PTEID_ID *IDData		/**< out: the address of a PTEID_ID struct */
);

/**
 * Read the Address data.
 */
PTEIDLIB_API long PTEID_GetAddr(
	PTEID_ADDR *AddrData	/**< out: the address of an PTEID_ADDR struct */
);

/**
 * Read the Picture.
 */
PTEIDLIB_API long PTEID_GetPic(
	PTEID_PIC *PicData		/**< out: the address of a PTEID_PIC struct */
);

/**
 * Read all the user and CA certificates.
 */
PTEIDLIB_API long PTEID_GetCertificates(
	PTEID_Certifs *Certifs	/**< out: the address of a PTEID_Certifs struct */
);

/**
 * Verify a PIN.
 */
PTEIDLIB_API long PTEID_VerifyPIN(
	unsigned char PinId,	/**< in: the PIN ID, see the PTEID_Pins struct */
	char *Pin,				/**< in: the PIN value, if NULL then the user will be prompted for the PIN */
	long *triesLeft			/**< out: the remaining PIN tries */
);

/**
 * Verify a PIN. If this is the signature PIN, do not display an alert message.
 */
PTEIDLIB_API long PTEID_VerifyPIN_No_Alert(
	unsigned char PinId,	/**< in: the PIN ID, see the PTEID_Pins struct */
	char *Pin,				/**< in: the PIN value, if NULL then the user will be prompted for the PIN */
	long *triesLeft			/**< out: the remaining PIN tries */
);

/**
 * Change a PIN.
 */
PTEIDLIB_API long PTEID_ChangePIN(
	unsigned char PinId,	/**< in: the PIN ID, see the PTEID_Pins struct */
	char *pszOldPin,		/**< in: the current PIN value, if NULL then the user will be prompted for the PIN */
	char *pszNewPin,		/**< in: the new PIN value, if NULL then the user will be prompted for the PIN */
	long *triesLeft			/**< out: the remaining PIN tries */
);

/**
 * Return the PINs (that are listed in the PKCS15 files).
 */
PTEIDLIB_API long PTEID_GetPINs(
	PTEID_Pins *Pins		/**< out: the address of a PTEID_Pins struct */
);

/**
 * Return the PKCS15 TokenInfo contents.
 */
PTEIDLIB_API long PTEID_GetTokenInfo(
	PTEID_TokenInfo *tokenData	/**< out: the address of a PTEID_TokenInfo struct */
);

/**
 * Read the contents of the SOD file from the card.
 * This function calls PTEID_ReadFile() with the SOD file as path.
 * If *outlen is less then the file's contents, only *outlen
 * bytes will be read. If *outlen is bigger then the file's
 * contents then the file's contents are returned without error. */
PTEIDLIB_API long PTEID_ReadSOD(
	unsigned char *out,         /**< out: the buffer to hold the file contents */
	unsigned long *outlen		/**< in/out: number of bytes allocated/number of bytes read */
);

/**
 * Unblock PIN with PIN change.
 * If pszPuk == NULL or pszNewPin == NULL, a GUI is shown asking for the PUK and the new PIN
 */
PTEIDLIB_API long PTEID_UnblockPIN(
	unsigned char PinId,	/**< in: the PIN ID, see the PTEID_Pins struct */
	char *pszPuk,			/**< in: the PUK value, if NULL then the user will be prompted for the PUK */
	char *pszNewPin,		/**< in: the new PIN value, if NULL then the user will be prompted for the PIN */
	long *triesLeft			/**< out: the remaining PUK tries */
);

#define UNBLOCK_FLAG_NEW_PIN    1
#define UNBLOCK_FLAG_PUK_MERGE  2   // Only on pinpad readers

/**
 * Extended Unblock PIN functionality.
 * E.g. calling PTEID_UnblockPIN_Ext() with ulFlags = UNBLOCK_FLAG_NEW_PIN
 *   is the same as calling PTEID_UnblockPIN(...)
 */
PTEIDLIB_API long PTEID_UnblockPIN_Ext(
	unsigned char PinId,	/**< in: the PIN ID, see the PTEID_Pins struct */
	char *pszPuk,			/**< in: the PUK value, if NULL then the user will be prompted for the PUK */
	char *pszNewPin,		/**< in: the new PIN value, if NULL then the user will be prompted for the PIN */
	long *triesLeft,		/**< out: the remaining PUK tries */
	unsigned long ulFlags	/**< in: flags: 0, UNBLOCK_FLAG_NEW_PIN, UNBLOCK_FLAG_PUK_MERGE or
									UNBLOCK_FLAG_NEW_PIN | UNBLOCK_FLAG_PUK_MERGE */
);

/**
 * Select an Application Directory File (ADF) by means of the AID (Application ID).
 */
PTEIDLIB_API long PTEID_SelectADF(
	unsigned char *adf,		/**< in: the AID of the ADF */
	long adflen				/**< in: the length */
);

/**
 * Read a file on the card.
 * If a PIN reference is provided and needed to read the file,
 * the PIN will be asked and checked if needed.
 * If *outlen is less then the file's contents, only *outlen
 * bytes will be read. If *outlen is bigger then the file's
 * contents then the file's contents are returned without error.
 */
PTEIDLIB_API long PTEID_ReadFile(
	unsigned char *file,	/**< in: a byte array containing the file path,
								e.g. {0x3F, 0x00, 0x5F, 0x00, 0xEF, 0x02} for the ID file */
	int filelen,			/**< in: file length */
	unsigned char *out,		/**< out: the buffer to hold the file contents */
	unsigned long *outlen,	/**< in/out: number of bytes allocated/number of bytes read */
	unsigned char PinId		/**< in: the ID of the Address PIN (only needed when reading the Address File) */
);

/**
 * Write data to a file on the card.
 * If a PIN reference is provided, the PIN will be asked and checked
 * if needed (just-in-time checking).
 * This function is only applicable for writing to the Personal Data file.
 */
PTEIDLIB_API long PTEID_WriteFile(
	unsigned char *file,	/**< in: a byte array containing the file path,
								e.g. {0x3F, 0x00, 0x5F, 0x00, 0xEF, 0x02} for the ID file */
	int filelen,			/**< in: file length */
	unsigned char *in,		/**< in: the data to be written to the file */
	unsigned long inlen,	/**< in: length of the data to be written */
	unsigned char PinId		/**< in: the ID of the Authentication PIN, see the PTEID_Pins struct */
);

PTEIDLIB_API long PTEID_WriteFile_inOffset(
	unsigned char *file,	/**< in: a byte array containing the file path,
								e.g. {0x3F, 0x00, 0x5F, 0x00, 0xEF, 0x02} for the ID file */
	int filelen,			/**< in: file length */
	unsigned char *in,		/**< in: the data to be written to the file */
	unsigned long inOffset, /**< in: the offset of the data to be written to the file */
	unsigned long inlen,	/**< in: length of the data to be written */
	unsigned char PinId		/**< in: the ID of the Authentication PIN, see the PTEID_Pins struct */
);


/**
 * Get the activation status of the card.
 */
PTEIDLIB_API long PTEID_IsActivated(
	unsigned long *pulStatus	/**< out the activation status: 0 if not activate, 1 if activated */
);

#define MODE_ACTIVATE_BLOCK_PIN   1

/**
 * Activate the card (= update a specific file on the card).
 * If the card has been activated allready, SC_ERROR_NOT_ALLOWED is returned.
 */
PTEIDLIB_API long PTEID_Activate(
	char *pszPin,			/**< in: the value of the Activation PIN */
	unsigned char *pucDate,	/**< in: the current date in DD MM YY YY format in BCD format (4 bytes),
									e.g. {0x17 0x11 0x20 0x06} for the 17th of Nov. 2006) */
	unsigned long ulMode	/**<in: mode: MODE_ACTIVATE_BLOCK_PIN to block the Activation PIN,
									or to 0 otherwise (this should only to be used for testing). */
);

/**
* Turn on/off SOD checking.
* 'SOD' checking means that the validity of the ID data,
* address data, the photo and the card authentication public
* key is checked to ensure it is not forged.
* This is done by reading the SOD file which contains hashes
* over the above data and is signed by a DocumentSigner
* certificate.
*/
PTEIDLIB_API long PTEID_SetSODChecking(
	int bDoCheck	/**< in: true to turn on SOD checking, false to turn it off */
);

/**
 * Specify the (root) certificates that are used to sign
 * the DocumentSigner certificates in the SOD file.
 * (The SOD file in the card is signed by a Document
 *  Signer cert, and this cert is inside the SOD as well).
 *
 * By default, this library reads the certificates that are
 * present in the %appdir%/eidstore/certs dir (in which %appdir%
 * is the directory in which the application resides.
 * So if this directory doesn't exist (or doesn't contain the
 * correct cert for the card), you should call this function
 * to specify them; or turn off SOD checkking with the
 * PTEID_SetSODChecking() function.
 * If you call this function
 * If you call this function again with NULL as parameter,
 * then the default CA certs will be used again.
 */
PTEIDLIB_API long PTEID_SetSODCAs(
	PTEID_Certifs *Certifs	/**< in: the address of a PTEID_Certifs, or NULL */
);

/**
 * Get the public key 'card authentication' key which can
 * be used to verify the authenticity of the eID card.
 * This public key is encoded in the ID file, and should
 * not be confused with the 'Citizen Authentication key'.
 *
 * No memory allocation will be done for the PTEID_RSAPublicKey struct,
 * so the 'modulus' and 'exponent' fields should have sufficiently memory
 * allocated to hold the respective values; and the amount of memory
 * should be given in the 'Length' fields. For example:
 *   unsigned char modulus[128];
 *   unsigned char exponent[3];
 *   PTEID_RSAPublicKey cardPubKey = {modulus, sizeof(modulus), exponent, sizeof(exponent)};
 */
PTEIDLIB_API long PTEID_GetCardAuthenticationKey(
	PTEID_RSAPublicKey *pCardAuthPubKey	/**< in: the address of a PTEID_RSAPublicKey struct */
);

/**
* Get the CVC CA public key that this card uses to verify the CVC key;
* allowing the application to select the correct CVC certificate for
* this card.
*
* No memory allocation will be done for the PTEID_RSAPublicKey struct,
* so the 'modulus' and 'exponent' fields should have sufficiently memory
* allocated to hold the respective values; and the amount of memory
* should be given in the 'Length' fields. For example:
*   unsigned char modulus[128];
*   unsigned char exponent[3];
*   PTEID_RSAPublicKey CVCRootKey = {modulus, sizeof(modulus), exponent, sizeof(exponent)};
*
* Upon successfull return, the modulusLength and exponentLength fields
* will contain the effective modulus length resp. exponent length.
*/
PTEIDLIB_API long PTEID_GetCVCRoot(
	PTEID_RSAPublicKey *pCVCRootKey	/**< in: the address of a PTEID_RSAPublicKey struct */
);

/**
 * Start a CVC authentication with the card.
 * The resuling challenge should be signed with the private key corresponding
 * to the CVC certificate (raw RSA signature) and provided in the
 * PTEID_CVC_Authenticate() function.
 */
PTEIDLIB_API long PTEID_CVC_Init(
    const unsigned char *pucCert,	/**< in: the CVC as a byte array */
    int iCertLen,					/**< in: the length of ucCert */
    unsigned char *pucChallenge,	/**< out: the challenge to be signed by the CVC private key */
    int iChallengeLen				/**< in: the length reserved for ucChallenge, must be 128 */
);

/**
 * Finish the CVC authentication with the card, to be called
 *   after a PTEID_CVC_Init()
 * Parameters:
 *   ucSignedChallenge:  (IN)  the challenge that was signed by the
 *                             private key corresponding to the CVC
 *   iSignedChallengeLen (IN)  the length of ucSignedChallenge, must be 128
 */
PTEIDLIB_API long PTEID_CVC_Authenticate(
    unsigned char *pucSignedChallenge,	/**< in: the challenge that was signed by the
											private key corresponding to the CVC */
    int iSignedChallengeLen				/**< in: the length of ucSignedChallenge, must be 128 */
);

/** Only for IAS 1.0.1 cards, 1st to be executed starting a secure session using symmetric keys */
PTEIDLIB_API long PTEID_CVC_Init_SM101(
    unsigned char *pucChallenge,	/**< out: the challenge to be signed by the derived symmetric key */
    int iChallengeLen				/**< in: the length reserved for pucChallenge, must be 48 */
);

/** Only for IAS 1.0.1 cards, 2nd command to be executed starting a secure session using symmetric keys */
PTEIDLIB_API long PTEID_CVC_Authenticate_SM101(
	const unsigned char *ifdChallenge,	/**< in:  the challenge that was signed by the derived symmetric key */
	int ifdChallengeLen,				/**< in:  the length of ifdChallenge, must be 48 */
	const char *ifdSerialNr,			/**< in:  the serial number of the terminal */
	int ifdSerialNrLen,					/**< in:  the length of the terminal serial number */
	const char *iccSerialNr,			/**< in:  the serial number of the card */
	int iccSerialNrLen,					/**< in:  the length of the card serial number */
	const unsigned char *keyIfd,		/**< in:  the secret key kIFD generated by the terminal */
	int keyIfdLen,						/**< in:  the length of the secret key kIFD */
	const unsigned char * encKey,		/**< in:  the derived encoding key for mutual authentication */
	unsigned int encKeyLen,				/**< in:  the length of the derived encoding key */
	const unsigned char * macKey,		/**< in:  the derived MAC key for mutual authentication */
	unsigned int macKeyLen,				/**< in:  the length of the derived MAC key */
	unsigned char *ifdChallengeResp,	/**< out: the response from the card to the authentication */
	int * ifdChallengeRespLen			/**< out: the length of ifdChallengeResponse, must be at least 48 */					
);

/**
 * Read out the contents of a file over a 'CVC channel'.
 * A successfull PTEID_CVC_Init() and PTEID_CVC_Authenticate()
 * must have been done before.
 * If *outlen is less then the file's contents, only *outlen
 * bytes will be read. If *outlen is bigger then the file's
 * contents then the file's contents are returned without error.
 */
PTEIDLIB_API long PTEID_CVC_ReadFile(
	unsigned char *file,	/**< in: the path of the file to read (e.g. {0x3F, 0x00, 0x5F, 0x00, 0xEF, 0x05} */
	int filelen,			/**< in: the length file path (e.g. 6) */
    unsigned char *out,		/**< out: the buffer to contain the file contents */
    unsigned long *outlen	/**< out the number of bytes to read/the number of byte read. */
);

#define CVC_WRITE_MODE_PAD    1

/**
 * Write to a file on the card over a 'CVC channel'.
 * A successfull PTEID_CVC_Init() and PTEID_CVC_Authenticate()
 * must have been done before.
 */
PTEIDLIB_API long PTEID_CVC_WriteFile(
	unsigned char *file,		/**< in: the path of the file to read (e.g. {0x3F, 0x00, 0x5F, 0x00, 0xEF, 0x05} */
	int filelen,				/**< in: the length file path (e.g. 6) */
	unsigned long ulFileOffset,	/**< in: at which offset in the file to start writing */
    const unsigned char *in,	/**< in: the file contents */
    unsigned long inlen,		/**< in: the number of bytes to write */
    unsigned long ulMode		/**< in: set to CVC_WRITE_MODE_PAD to pad the file with zeros if
									(ulFileOffset + inlen) is less then the file length */
);

/**
 * Read the address file over a 'CVC channel' and put the contents
 * into a PTEID_ADDR struct.
 * A successfull PTEID_CVC_Init() and PTEID_CVC_Authenticate()
 * must have been done before.
 */
PTEIDLIB_API long PTEID_CVC_GetAddr(
	PTEID_ADDR *AddrData	/**< out: the address of a PTEID_ADDR struct */
);

/**
 * Write to the address file over a 'CVC channel'
 * A successfull PTEID_CVC_Init() and PTEID_CVC_Authenticate()
 * must have been done before.
 * Remark: the address data will be padded with 0 bytes up to
 * the length of the address file.
 */
PTEIDLIB_API long PTEID_CVC_WriteAddr(
	const PTEID_ADDR *AddrData	/**< in: the address of a PTEID_ADDR struct */
);

/**
 * This function calls PTEID_CVC_WriteFile() with the SOD file as path.
 */
PTEIDLIB_API long PTEID_CVC_WriteSOD(
	unsigned long ulFileOffset,	/**< in: at which offset in the file to start writing */
    const unsigned char *in,	/**< in: the file contents */
    unsigned long inlen,		/**< in: the number of bytes to write */
    unsigned long ulMode		/**< in: set to CVC_WRITE_MODE_PAD to pad the file with zeros if 
									(ulFileOffset + inlen) is less then the file length */
);

/////////////////////////// Remote CVC functions /////////////////////////

/*
The functions below can be used to help set up a secure channel between
the card and a Server (e.g. Address Change Server).
More specifically, the following steps need to be done:
- PTEID_CVC_R_Init()
- PTEID_CVC_R_DH_Auth()
- PTEID_CVC_R_ValidateSignature()
- a sequence of SendApdu() calls
(the _R_ stands for 'Remote'
*/

/**
 * Get the DH parameters, to be sent to the Server
 */
PTEIDLIB_API long PTEID_CVC_R_Init(
	unsigned char *ucG,       /**< out: the G parameter */
	unsigned long *outlenG,   /**< in:out length of G parameter, should be 128 */
	unsigned char *ucP,       /**< out: the P parameter */
	unsigned long *outlenP,   /**< in:out length of P parameter, should be 128 */
	unsigned char *ucQ,       /**< out: the Q parameter */
	unsigned long *outlenQ    /**< in/out length of Q parameter, should be 20 */
);

/**
 * Complete the DH key agreement and ask the card for a challenge.
 * More in detail, the following is done:
 * - Check the CVC cert (pucCert) by means of the CA pubkey on the card
 * - Send Kifd (received from the Server) to the card
 * - Read out Kicc, should be sent to the Server
 * - Send the CVC cert to the card for verification
 * - Tell the card to use the public key in the CVC cert
 * - Ask a challenge to the card, should be sent to the Server
 */
PTEIDLIB_API long PTEID_CVC_R_DH_Auth(
	const unsigned char *ucKifd,    /**<in: Kifd, sent by the Server */
	unsigned long ulKifdLen,        /**<in: Length of Kifd, should be 128 */
	const unsigned char *pucCert,   /**<in: CVC cert */
	unsigned long ulCertLen,        /**<in: CVC cert length */
	unsigned char *ucKicc,          /**<out: Kicc, to be sent to the server */
	unsigned long *ulKiccLen,       /**<out: Kicc length, should be 128 */
	unsigned char *ucChallenge,     /**<out: challenge, to be sent to the server */
	unsigned long *ulChallengeLen); /**<out: challenge length, should be 128 */

/**
 * Provide the signed challenge (made by the Server)
 * to the card.
 */
PTEIDLIB_API long PTEID_CVC_R_ValidateSignature(
	const unsigned char *pucSignedChallenge,  /**< in: the signed challenge */
	unsigned long ulSignedChallengeLen);     /**< in: the length of the signed challenge, should be 128 */

/**
 * Send an APDU to the card, see ISO7816-4 for more info.
 * - For a case 1 APDU: ulRequestLen should be 4, ulResponseLen should be at least 2 (for SW1-SW2)
 * - For a case 2 APDU: ulRequestLen should be 5
 * - For a case 3 APDU: ucRequest[4] + 5 should equal ulRequestLen, ulResponseLen should be at least 2 (for SW1-SW2)
 * - For a case 4 APDU: ucRequest[4] + 5 should equal ucRequestLen + 1, the last by is the 'Le' value
 * If the call has been successfull, ucResponse should always contain SW1 and SW2 at the end.
 */
PTEIDLIB_API long PTEID_SendAPDU(
	const unsigned char *ucRequest, /**<in: command APDU */
	unsigned long ulRequestLen,     /**<in: command APDU length */
	unsigned char *ucResponse,      /**<out: response APDU */
	unsigned long *ulResponseLen);  /**<in/out: response APDU length */




/**
 * Do an address change, this function will connect the Address Change Server
 * and forward commands between the Card and the Address Change Server.
 */
PTEIDLIB_API long PTEID_ChangeAddress(
	const char *csServer,                  /**<in: Address Change Server, format: <name>:<port> */
	const unsigned char *ucServerCaCert,   /**<in: CA cert of the Server (DER encoded) */
	unsigned long ulServerCaCertLen,       /**<in: length of the Server CA cert */
	tProxyInfo *proxyInfo,                 /**<in: proxy info, or NULL if no proxy is needed */
	const char *csSecretCode,              /**<in: the Secret code that the citizen received */
	const char* csProcess);                /**<in: the Process code that the citizen received */

/**
 * Returns info on what the PTEID_ChangeAddress() is currently doing.
 */
PTEIDLIB_API tAddressChangeState PTEID_GetChangeAddressProgress();

/**
 * Setup a callback function to be called during the change address operation when state changes.
 */
PTEIDLIB_API void PTEID_SetChangeAddressCallback(void(_USERENTRY * callback)(tAddressChangeState state));

/**
 * Allows the library user to cancel a running Change Address operation.
 */
PTEIDLIB_API void PTEID_CancelChangeAddress();



/**
 * Do a CAP PIN change, this function will connect the CAP PIN Change Server
 * and forward commands between the Card and the CAP PIN Server.
 */
PTEIDLIB_API long PTEID_CAP_ChangeCapPin(
	const char *csServer,					/**<in: Address Change Server, format: <name>:<port>. */
	const unsigned char *ucServerCaCert,	/**<in: CA cert of the Server (DER encoded). */
	unsigned long ulServerCaCertLen,		/**<in: length of the Server CA cert. */
	tProxyInfo *proxyInfo,					/**<in: proxy info, or NULL if no proxy is needed. */
	const char *pszOldPin,					/**<in: the current CAP PIN. */
	const char *pszNewPin,					/**<in: the new CAP PIN we want to change to. */
	long *triesLeft);						/**<out: The tries left after an unsuccessful attempt. */

/**
 * Returns info on what the PTEID_ChangeCapPin() is currently doing.
 */
PTEIDLIB_API tCapPinChangeState PTEID_CAP_GetCapPinChangeProgress();

/**
 * Setup a callback function to be called during the change address operation when state changes.
 */
PTEIDLIB_API void PTEID_CAP_SetCapPinChangeCallback(void(_USERENTRY * callback)(tCapPinChangeState state));

/**
 * Allows the library user to cancel a running Change CAP PIN operation.
 */
PTEIDLIB_API void PTEID_CAP_CancelCapPinChange();

/**
 * Returns the latest error code received or sent by Change Address or CAP PIN change.
 */
PTEIDLIB_API tWebErrorCode PTEID_GetLastWebErrorCode();

/**
 * Returns the lastest error message received or sent by Change Address or CAP PIN change.
 */
PTEIDLIB_API const char * PTEID_GetLastWebErrorMessage();


#ifdef __cplusplus
}
#endif

#endif // __PTEIDLIB_H__
