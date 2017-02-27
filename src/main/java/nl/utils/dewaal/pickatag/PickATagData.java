/**
 * 
 */
package nl.utils.dewaal.pickatag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsefa.Deserializer;
import org.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;

public class PickATagData {

	private static BufferedReader _br;
	private static Reader _r;

	private static final Logger log = LogManager.getLogger(PickATagData.class);

	public static void main(String[] args) {
		PickATagData patD = new PickATagData();
		log.debug(patD.getTagLine());

	}

	private int _recordCount;

	private String _fileName = Messages.getString("PickATagData.FileName"); //$NON-NLS-1$

	public PickATagData() {
	}

	private void connectDB() {

		if (!this.isConnected()) {
			try
			{
				InputStream is = this.getClass().getResourceAsStream(_fileName);
				Reader _r = new InputStreamReader(is, "UTF-8");
				_br = new BufferedReader(_r);
			}
			catch (UnsupportedEncodingException e) {
				log.error("File not found: " + e.getLocalizedMessage());
			}
			catch (NullPointerException npe) {
				log.error("File not found: " + _fileName);
			}

		}
	}

	private void disconnectDB() {
		if (this.isConnected()) {
			log.debug(Messages.getString("PickATagData.messageClosingDB")); //$NON-NLS-1$
			try
			{
				_r.close();
				_br.close();
				_r = null;
				_br = null;

			}
			catch (Exception e)
			{
				//TODO proper exception handling here
				log.error ("ups");
			}
		}
	}

	public int get_recordCount() {
		return _recordCount;
	}

	private int getRandomNumber(int max) {
		Random generator = new Random();
		int randomIndex = generator.nextInt(max) + 1;
		//TODO: info logger + uit resourcebundel
		log.debug("Randomly determined tagline ID: " + randomIndex);
		return randomIndex;
	}

	private int getRandomTagNumber() {
		// first, determine the number of records...
		int nrRecords;
		nrRecords = getRecordcount();
		if (nrRecords != 0) {
			//return 11;
			return getRandomNumber(getRecordcount());
		} else {
			log.error(Messages.getString("PickATagData.ResultZero")); //$NON-NLS-1$
			return 0;
		}

	}

	private String getRecord(int recordNumber) {
		String result = Messages.getString("PickATagData.EmptyString"); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		if (!isConnected()) connectDB();
		if (_br==null)
			return result;
		int i = 0;
		try {
			sb.append(_br.readLine() + "\n");
			String line = sb.toString();
			while (line != null) {
				i++;
				if (i==recordNumber)
				{
					sb.append(line);
				}
				//continue otherwise
				line = _br.readLine();
			}
			result = sb.toString();
			log.debug(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getLocalizedMessage());
		} 
		return result;
	}

	private int getRecordcount() {
		log.debug("in getRecordcount()");
		if (_recordCount == 0)
		{
			if (!this.isConnected())
				this.connectDB();
			if (_br==null) 
				return 0;
			int i = 0;
			try {
				while (_br.readLine() != null) 
					i++;
				//String everything = sb.toString();
				//TODO resourcen!
				log.debug("Recordcount (newly counted): " + i);
				this.set_recordCount(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			}
			disconnectDB();
			return i;
		}
		else return _recordCount;
	}

	public String getTagLine() {
		String strResultaat = this
				.getRecord(this.getRandomTagNumber())
				.replaceAll(
						Messages.getString("PickATagData.strReplacePar"), Messages.getString("PickATagData.strReplaceNewline")); //$NON-NLS-1$ //$NON-NLS-2$
		disconnectDB();
		return interpretCSV(strResultaat);
	}

	private String interpretCSV (String CSVInput)
	{
		String terug = "";
		CsvConfiguration csvConfiguration = new CsvConfiguration();
		csvConfiguration.setFieldDelimiter(',');
		csvConfiguration.setLineFilter(new HeaderAndFooterFilter(1, false, false));

		Deserializer deserializer = CsvIOFactory.createFactory(csvConfiguration, Row.class).createDeserializer();

		deserializer.open(new StringReader(CSVInput));
		while (deserializer.hasNext()) {
			Row row = deserializer.next();
			terug = row.tagLineText;
			log.info("tagLineText = " + row.tagLineText);
			// do something useful with it
		}
		deserializer.close(true);
		return terug;

	}

	private boolean isConnected() {
		// function to see whether this class has an active file connection:
		log.debug("Check for connection will yield: " + (_br !=null && _r!=null));
		if (_br != null && _r !=null)
			return true;
		return false;
	}

	public void set_recordCount(int _recordCount) {
		this._recordCount = _recordCount;
	}

}
