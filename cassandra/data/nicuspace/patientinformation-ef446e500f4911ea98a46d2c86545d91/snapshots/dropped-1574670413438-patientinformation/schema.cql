CREATE TABLE IF NOT EXISTS nicuspace.patientinformation (
	updatetime timestamp PRIMARY KEY,
	active boolean,
	bednumber int,
	birthday timestamp,
	caseid text,
	gender text,
	idnumber text,
	name text,
	pregnant timestamp,
	pregant timestamp,
	hisid text)
	WITH ID = ef446e50-0f49-11ea-98a4-6d2c86545d91
	AND bloom_filter_fp_chance = 0.01
	AND dclocal_read_repair_chance = 0.1
	AND crc_check_chance = 1.0
	AND default_time_to_live = 0
	AND gc_grace_seconds = 864000
	AND min_index_interval = 128
	AND max_index_interval = 2048
	AND memtable_flush_period_in_ms = 0
	AND read_repair_chance = 0.0
	AND speculative_retry = '99PERCENTILE'
	AND comment = ''
	AND caching = { 'keys': 'ALL', 'rows_per_partition': 'NONE' }
	AND compaction = { 'max_threshold': '32', 'min_threshold': '4', 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
	AND compression = { 'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor' }
	AND cdc = false
	AND extensions = {  };
ALTER TABLE nicuspace.patientinformation DROP pregant USING TIMESTAMP 1574668498045000;
ALTER TABLE nicuspace.patientinformation DROP hisid USING TIMESTAMP 1574668618586000;
